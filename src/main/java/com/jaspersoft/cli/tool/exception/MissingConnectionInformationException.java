package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */

///////// delete this class!
// fixme: violation of the YAGNI principle!!!
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
