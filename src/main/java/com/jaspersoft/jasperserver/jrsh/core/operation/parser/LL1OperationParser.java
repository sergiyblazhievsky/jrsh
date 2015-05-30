package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationReflector;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer.DefaultLexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.List;

/**
 * @author Alex Krasnyanskiy
 */
@Log4j
public class LL1OperationParser implements OperationParser {

    @Setter
    private Lexer lexer;

    public LL1OperationParser() {
        lexer = new DefaultLexer();
    }

    /**
     * Parses string representation of operation into
     * the operation.
     *
     * @param line string representation of operation
     * @return operation instance
     * @throws OperationParseException
     */
    public Operation parse(String line) throws OperationParseException {
        List<String> inputTokens = lexer.getTokens(line);
        String operationName = inputTokens.get(0);

        Operation operation = OperationFactory.createOperationByName(operationName);
        Conditions.checkOperation(operation);
        //
        // Retrieve grammar from operation metadata
        // via annotations
        //
        Grammar grammar = OperationGrammarParser.parse(operation);
        List<Rule> grammarRules = grammar.getRules();
        boolean matchedRuleExist = false;
        //
        // Match tokens and configure operation instance
        //
        for (Rule rule : grammarRules) {
            List<Token> ruleTokens = rule.getTokens();
            if (match(ruleTokens, inputTokens)) {
                OperationReflector.set(operation, ruleTokens, inputTokens);
                matchedRuleExist = true;
            }
        }
        //
        // if unmatched then throw exception
        //
        Conditions.checkMatchedRulesFlag(matchedRuleExist);
        return operation;
    }

    /**
     * Match rule tokens to input tokens.
     *
     * @param ruleTokens  rule tokens
     * @param inputTokens input tokens
     * @return true if matched
     */
    protected boolean match(List<Token> ruleTokens, List<String> inputTokens) {
        //
        // Check tokens length
        //
        if (ruleTokens.size() != inputTokens.size()) {
            return false;
        }
        //
        // if at least one token in rule is unmatched,
        // return `false` immediately
        //
        for (int i = 0; i < ruleTokens.size(); i++) {
            if (!ruleTokens.get(i).match(inputTokens.get(i))) {
                return false;
            }
        }
        return true;
    }
}
