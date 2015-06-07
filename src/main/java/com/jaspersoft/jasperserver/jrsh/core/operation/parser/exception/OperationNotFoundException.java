package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class OperationNotFoundException extends OperationParseException {
    public OperationNotFoundException() {
        super("Operation not found");
    }
}
