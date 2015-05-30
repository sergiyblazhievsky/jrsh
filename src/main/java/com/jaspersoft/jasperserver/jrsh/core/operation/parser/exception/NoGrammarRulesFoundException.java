package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class NoGrammarRulesFoundException extends OperationParseException {
    public NoGrammarRulesFoundException() {
        super("Could not find grammar rules for given operation");
    }
}
