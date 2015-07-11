
package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationStateConfigurer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.rule.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.lexer.Lexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.lexer.PathIdentifyingLexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import lombok.Setter;

import java.util.List;

public class LL1OperationParser implements OperationParser {

    @Setter private Lexer lexer;

    public LL1OperationParser() {
        lexer = new PathIdentifyingLexer();
    }

    public Operation parse(String line) throws OperationParseException {
        List<String> inputTokens = lexer.convert(line);
        String operationName = inputTokens.get(0);
        Operation operation = OperationFactory.createOperationByName(operationName);
        Conditions.checkOperation(operation);

        Grammar grammar = OperationGrammarParser.parse(operation);
        List<Rule> grammarRules = grammar.getRules();
        boolean matchedRuleExist = false;
        for (Rule rule : grammarRules) {
            List<Token> ruleTokens = rule.getTokens();
            if (match(ruleTokens, inputTokens)) {
                OperationStateConfigurer.configure(operation, ruleTokens, inputTokens);
                matchedRuleExist = true;
            }
        }
        Conditions.checkMatchedRulesFlag(matchedRuleExist);
        return operation;
    }

    protected boolean match(List<Token> ruleTokens, List<String> inputTokens) {
        if (ruleTokens.size() != inputTokens.size()) {
            return false;
        }
        for (int i = 0; i < ruleTokens.size(); i++) {
            if (!ruleTokens.get(i).match(inputTokens.get(i))) {
                return false;
            }
        }
        return true;
    }
}
