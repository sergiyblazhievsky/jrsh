package com.jaspersoft.jasperserver.jrsh.core.completion;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.rule.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;

import java.util.List;

public class CompleterBuilder { // Builder? Really?

    private AggregateCompleter aggregator;

    public CompleterBuilder() {
        this.aggregator = new AggregateCompleter();
    }

    //
    // Need refactoring
    //
    public CompleterBuilder withOperationGrammar(Grammar grammar) {
        List<Rule> rules = grammar.getRules();
        ArgumentCompleter ruleCompleter = new ArgumentCompleter();
        for (Rule rule : rules) {
            List<Token> tokens = rule.getTokens();
            for (Token token : tokens) {
                Completer completer = token.getCompleter();
                ruleCompleter.getCompleters().add(completer);
            }
            ruleCompleter.getCompleters().add(new NullCompleter());
            aggregator.getCompleters().add(ruleCompleter);
            ruleCompleter = new ArgumentCompleter();
        }
        return this;
    }

    public Completer build() {
        return aggregator;
    }
}
