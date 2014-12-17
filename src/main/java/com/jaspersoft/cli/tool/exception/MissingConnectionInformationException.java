package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class MissingConnectionInformationException extends RuntimeException {

    private final static String MESSAGE = "You haven't specified JRS url, password, and username.";

    public MissingConnectionInformationException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
