package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationStateConfigurer;
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
 * It's a context-free language parser. which is used to parse an
 * input to the operation. For more details about LL(k) parser please
 * follow the link: https://en.wikipedia.org/?title=LL_parser
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
@Log4j
public class LL1OperationParser implements OperationParser {

    @Setter
    private Lexer lexer;

    public LL1OperationParser() {
        lexer = new DefaultLexer();
    }

    /**
     * Parse the line into fully configured operation
     * with options.
     *
     * @param line input
     * @return operation
     * @throws OperationParseException
     */
    public Operation parse(String line) throws OperationParseException {
        List<String> inputTokens = lexer.convert(line);
        String operationName = inputTokens.get(0);
        Operation operation = OperationFactory.createOperationByName(operationName);
        Conditions.checkOperation(operation);

        Grammar grammar = OperationGrammarParser.parse(operation);
        List<Rule> grammarRules = grammar.getRules();
        boolean matchedRuleExist = false;
        //
        // Match tokens and configure operation instance
        //
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
