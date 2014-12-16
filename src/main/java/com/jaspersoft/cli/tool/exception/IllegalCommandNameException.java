package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class IllegalCommandNameException extends RuntimeException {

    private final static String MESSAGE = "You entered the wrong command name.";

    public IllegalCommandNameException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
