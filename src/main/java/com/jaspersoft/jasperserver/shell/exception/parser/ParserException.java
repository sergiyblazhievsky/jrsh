package com.jaspersoft.jasperserver.shell.exception.parser;

import com.jaspersoft.jasperserver.shell.exception.InterfaceException;

/**
 * @author Alexander Krasnyanskiy
 */
public abstract class ParserException extends InterfaceException {

    public ParserException(String message) {
        super(message);
    }
}
