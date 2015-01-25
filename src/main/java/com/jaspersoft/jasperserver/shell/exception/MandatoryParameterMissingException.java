package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class MandatoryParameterMissingException extends InterfaceException {

    public MandatoryParameterMissingException() {
        super("You have not specified mandatory parameter.");
    }
}
