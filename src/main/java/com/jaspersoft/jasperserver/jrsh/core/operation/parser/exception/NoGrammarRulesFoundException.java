package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

/**
 * @author Alex Krasnyanskiy
 */
public class NoGrammarRulesFoundException extends OperationParseException {

    private static final Messages messages = new Messages("i18n/error");

    public NoGrammarRulesFoundException() {
        super(messages.getMessage("message.grammar.error"));
    }
}
