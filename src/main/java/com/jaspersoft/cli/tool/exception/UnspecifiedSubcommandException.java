package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
public class UnspecifiedSubcommandException extends RuntimeException {

    private final static String MESSAGE = "Please specify subcommand.";

    public UnspecifiedSubcommandException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

