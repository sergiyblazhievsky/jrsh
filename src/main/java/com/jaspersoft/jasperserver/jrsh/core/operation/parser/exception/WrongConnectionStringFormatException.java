package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class WrongConnectionStringFormatException extends OperationParseException {

    public WrongConnectionStringFormatException() {
        super("Connection string doesn't match the format. Should be [username%password@url].");
    }
}
