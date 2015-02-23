package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class NotSpecifiedProfileNameException extends InterfaceException {
    public NotSpecifiedProfileNameException() {
        super("You have not specified profile name.");
    }
}
