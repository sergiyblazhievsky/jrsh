package com.jaspersoft.jasperserver.shell.exception.parser;

import static java.lang.String.format;

/**
 * @author Alexander Krasnyanskiy
 */
public class UnknownInputContentException extends ParserException {

    public UnknownInputContentException(String input) {
        super(format("You've entered neither a command nor a parameter ('%s')", input));
    }
}
