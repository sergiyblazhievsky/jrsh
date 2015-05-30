package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class NoOperationFoundException extends OperationParseException {
    public NoOperationFoundException() {
        super("Operation not found.");
    }
}
