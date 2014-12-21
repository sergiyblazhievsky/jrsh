package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class WrongPathException extends RuntimeException {

    private final static String MESSAGE = "You've specified a wrong path.";

    public WrongPathException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

