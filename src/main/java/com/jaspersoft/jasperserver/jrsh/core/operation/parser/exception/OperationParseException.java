package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

/**
 * @author Alex Krasnyanskiy
 */
public abstract class OperationParseException extends RuntimeException {
    public OperationParseException(String msg) {
        super(msg);
    }
}
