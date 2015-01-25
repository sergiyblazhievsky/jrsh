package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongPathParameterException extends IOException {

    public WrongPathParameterException() {
        super("You've specified wrong file path.");
    }
}
