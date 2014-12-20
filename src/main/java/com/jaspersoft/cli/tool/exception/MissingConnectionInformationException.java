package com.jaspersoft.cli.tool.exception;

/**
 * @author Alex Krasnyanskiy
 */
///////// delete this class! ////////////////
// fixme: грубое нарушение принципа YAGNI !!!
/////////////////////////////////////////////
public class MissingConnectionInformationException extends RuntimeException {

    private final static String MESSAGE = "You haven't specified JRS url/password/username.";

    public MissingConnectionInformationException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
