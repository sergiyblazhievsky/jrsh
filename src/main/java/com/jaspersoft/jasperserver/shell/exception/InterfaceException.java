package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public abstract class InterfaceException extends RuntimeException {
    public InterfaceException(String message) {
        super(message);
    }
}
