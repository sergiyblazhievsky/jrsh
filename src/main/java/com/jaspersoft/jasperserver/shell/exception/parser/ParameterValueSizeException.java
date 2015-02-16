package com.jaspersoft.jasperserver.shell.exception.parser;

import static java.lang.String.format;

/**
 * @author Alexander Krasnyanskiy
 */
public class ParameterValueSizeException extends ParserException {

    public ParameterValueSizeException(String paramName, String cmdName) {
        super(format("Multiple values not allowed for %s parameter of command '%s'", paramName, cmdName));
    }
}
