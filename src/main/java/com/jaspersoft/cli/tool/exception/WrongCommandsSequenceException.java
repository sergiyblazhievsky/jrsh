package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class WrongCommandsSequenceException extends RuntimeException {

    private final static String MESSAGE = "Wrong command sequence.";

    public WrongCommandsSequenceException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
