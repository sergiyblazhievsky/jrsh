package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;

/**
 * @author Alexander Krasnyanskiy
 */
public interface OperationParser {

    Operation parse(String line) throws OperationParseException;

}