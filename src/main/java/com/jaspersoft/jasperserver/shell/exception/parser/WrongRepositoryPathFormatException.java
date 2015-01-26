package com.jaspersoft.jasperserver.shell.exception.parser;

import com.jaspersoft.jasperserver.shell.exception.InterfaceException;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongRepositoryPathFormatException extends InterfaceException {

    public WrongRepositoryPathFormatException() {
        super("You've specified wrong path. Hint: your path cannot end with '/', but must begin form '/'.");
    }
}
