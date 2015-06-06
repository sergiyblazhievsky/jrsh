package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public abstract class OperationParseException extends RuntimeException {
    public OperationParseException(String message) {
        super(message);
    }
}
