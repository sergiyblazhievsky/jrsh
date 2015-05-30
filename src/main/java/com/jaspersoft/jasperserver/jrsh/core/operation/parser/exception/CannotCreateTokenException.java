package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

public class CannotCreateTokenException extends OperationParseException {
    public CannotCreateTokenException(Class<? extends Token> tokenType) {
        super(String.format("Could not create token from [%s] class", tokenType.getName()));
    }
}
