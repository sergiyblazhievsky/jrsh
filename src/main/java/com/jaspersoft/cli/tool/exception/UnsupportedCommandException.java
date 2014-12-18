package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class UnsupportedCommandException extends RuntimeException {

    private final static String MESSAGE = "Sorry, but this command not yet implemented.";

    public UnsupportedCommandException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
