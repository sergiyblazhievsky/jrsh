package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class SessionIsNotAvailableException extends InterfaceException {

    // wrong command sequence!

    public SessionIsNotAvailableException() {
        super("There is no active session.");
    }
}
