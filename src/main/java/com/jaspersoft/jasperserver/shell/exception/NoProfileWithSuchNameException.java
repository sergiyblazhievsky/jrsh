package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class NoProfileWithSuchNameException extends InterfaceException {

    public NoProfileWithSuchNameException(String wrongName) {
        super(String.format("No profile found for <%s>", wrongName));
    }
}
