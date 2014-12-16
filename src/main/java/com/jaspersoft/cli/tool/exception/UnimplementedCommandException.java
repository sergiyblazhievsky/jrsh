package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class UnimplementedCommandException extends RuntimeException {

    private final static String MESSAGE = "Sorry, but this command not yet implemented.";

    public UnimplementedCommandException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
