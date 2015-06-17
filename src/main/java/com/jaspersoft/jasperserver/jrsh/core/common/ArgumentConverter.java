package com.jaspersoft.jasperserver.jrsh.core.common;

import com.google.common.base.Joiner;
import com.jaspersoft.jasperserver.jrsh.core.common.exception.CouldNotOpenScriptFileException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.singletonList;
import static org.apache.commons.io.FileUtils.readLines;

/**
 * @author Alexander Krasnyanskiy
 */
public class ArgumentConverter {

    public static Script convertToScript(String[] args) {
        Script script;
        //
        // Parse arguments and convert them into ordered operations.
        //
        switch (args.length) {
            case 0: {
                script = new Script(singletonList("help"));
                break;
            }
            case 1: {
                String line = args[0];
                if (isConnectionString(line)) {
                    script = new Script(singletonList("login " + line));
                } else {
                    script = new Script(singletonList(line));
                }
                break;
            }
            default: {
                if ("--script".equals(args[0]) && isScriptFileName(args[1])) {
                    try {
                        List<String> lines = readLines(new File(args[1]));
                        script = new Script(lines);
                    } catch (IOException ignored) {
                        throw new CouldNotOpenScriptFileException(args[1]);
                    }
                } else if (isConnectionString(args[0])) {
                    //
                    // Add operation name to make it consistent with
                    // the correct Login format
                    //
                    String loginLine = "login " + args[0];
                    //
                    // Separate the next operation from Login
                    //
                    String nextLine = Joiner.on(" ").join(copyOfRange(args, 1, args.length));
                    //
                    // Build script consisting of two operations
                    //
                    script = new Script(asList(loginLine, nextLine));
                } else {
                    String line = Joiner.on(" ").join(args);
                    script = new Script(singletonList(line));
                }
            }
        }
        return script;
    }
}