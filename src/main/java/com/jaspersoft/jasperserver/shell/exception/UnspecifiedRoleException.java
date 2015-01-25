package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class UnspecifiedRoleException extends InterfaceException {

    public UnspecifiedRoleException() {
        super("You've not specified the role name.");
    }
}
