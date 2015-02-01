package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongPasswordException extends InterfaceException {

    public WrongPasswordException() {
        super("Wrong password.");
    }
}
