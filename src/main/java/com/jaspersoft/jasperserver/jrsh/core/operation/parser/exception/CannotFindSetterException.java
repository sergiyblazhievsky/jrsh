package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import static java.lang.String.format;

/**
 * @author Alexander Krasnyanskiy
 */
public class CannotFindSetterException extends OperationParseException {
    public CannotFindSetterException(String fieldName) {
        super(format("Could not find a setter for %s field", fieldName));
    }
}
