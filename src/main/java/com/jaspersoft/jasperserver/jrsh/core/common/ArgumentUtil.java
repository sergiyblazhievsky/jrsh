package com.jaspersoft.jasperserver.jrsh.core.common;

import com.jaspersoft.jasperserver.jrsh.core.common.exception.CouldNotOpenScriptFileException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Joiner.on;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.singletonList;
import static org.apache.commons.io.FileUtils.readLines;

public class ArgumentUtil {

    public static List<String> convertToScript(String[] arguments) {
        List<String> script;

        switch (arguments.length) {
            case 0: {
                script = singletonList("help");
                break;
            }

            case 1: {
                String line = arguments[0];
                script = isConnectionString(line)
                        ? singletonList(format("login %s", line))
                        : singletonList(line);
                break;
            }

            default: {
                if ("--script".equals(arguments[0]) && isScriptFileName(arguments[1])) {
                    try {
                        script = readLines(new File(arguments[1]));
                    } catch (IOException ignored) {
                        throw new CouldNotOpenScriptFileException(arguments[1]);
                    }
                }
                else if (isConnectionString(arguments[0])) {
                    String login = "login " + arguments[0];
                    String nextLine = on(" ").join(copyOfRange(arguments, 1, arguments.length));
                    script = asList(login, nextLine);
                }
                else {
                    String line = on(" ").join(arguments);
                    script = singletonList(line);
                }
            }
        }
        return script;
    }
}
