package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;

/**
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public interface OperationParser {

    /**
     * Parses the input into the operation.
     *
     * @param line input
     * @return operation
     * @throws OperationParseException
     */
    Operation parse(String line) throws OperationParseException;

}