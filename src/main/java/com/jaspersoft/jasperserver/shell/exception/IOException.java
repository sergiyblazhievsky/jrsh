package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public abstract class IOException extends RuntimeException {

    public IOException(String message) {
        super(message);
    }
}
