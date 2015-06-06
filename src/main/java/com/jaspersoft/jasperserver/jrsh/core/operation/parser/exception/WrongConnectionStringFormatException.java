package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongConnectionStringFormatException extends OperationParseException {
    public WrongConnectionStringFormatException() {
        super("Connection string doesn't match the format. Should be [name]|[org]%[password]@[url]");
    }
}
