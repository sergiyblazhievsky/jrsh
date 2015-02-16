package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class UnknownInterfaceException extends InterfaceException {

    public UnknownInterfaceException(String message) {
        super("error: " + message);
    }
}
