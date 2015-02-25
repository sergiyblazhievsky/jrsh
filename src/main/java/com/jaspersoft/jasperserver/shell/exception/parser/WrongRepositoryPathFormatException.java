package com.jaspersoft.jasperserver.shell.exception.parser;

import com.jaspersoft.jasperserver.shell.exception.InterfaceException;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongRepositoryPathFormatException extends InterfaceException {

    public WrongRepositoryPathFormatException() {
        super("You've specified wrong path.");
    }
}
