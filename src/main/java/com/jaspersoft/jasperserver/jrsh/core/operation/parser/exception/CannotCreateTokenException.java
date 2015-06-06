package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

import static java.lang.String.format;

/**
 * @author Alexander Krasnyanskiy
 */
public class CannotCreateTokenException extends OperationParseException {
    public CannotCreateTokenException(Class<? extends Token> tokenType) {
        super(format("Could not create a token of %s class", tokenType.getName()));
    }
}
