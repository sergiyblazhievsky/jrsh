package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class UnknownFormatOptionException extends RuntimeException {

    private final static String MESSAGE = "Sorry, but there is no such format option.";

    public UnknownFormatOptionException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

