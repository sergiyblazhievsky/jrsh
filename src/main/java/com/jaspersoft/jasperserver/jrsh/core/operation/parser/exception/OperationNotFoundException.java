package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class OperationNotFoundException extends OperationParseException {
    public OperationNotFoundException() {
        super("Operation not found");
    }
}
