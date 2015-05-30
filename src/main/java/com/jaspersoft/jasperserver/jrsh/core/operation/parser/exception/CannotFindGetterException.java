package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class CannotFindGetterException extends OperationParseException {
    public CannotFindGetterException(String msg) {
        super(String.format("Cannot find a setter for the field [%s]", msg));
    }
}
