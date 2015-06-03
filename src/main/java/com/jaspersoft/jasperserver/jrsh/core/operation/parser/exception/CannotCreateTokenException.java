package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

import static java.lang.String.format;

public class CannotCreateTokenException extends OperationParseException {

    private static final Messages messages = new Messages("i18n/error");

    public CannotCreateTokenException(Class<? extends Token> tokenType) {
        super(format(messages.getMessage("message.token.instantiation.error"), tokenType.getName()));
    }
}
