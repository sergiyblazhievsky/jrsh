package com.jaspersoft.jasperserver.shell.exception.parser;

import static java.lang.String.format;

/**
 * @author Alexander Krasnyanskiy
 */
public class NoSuchCommandException extends ParserException {

    public NoSuchCommandException(String cmdName) {
        super(format("[%s] is not a jrsh command.", cmdName));
    }
}
