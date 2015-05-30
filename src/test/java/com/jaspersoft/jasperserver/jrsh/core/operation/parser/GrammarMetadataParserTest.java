package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.InputToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;

public class GrammarMetadataParserTest {

    @Test
    public void shouldParseOperationMetadataAndReturnOperationGrammar() {
        Grammar grammar = OperationGrammarParser.parse(new LoginOperation());
        Assert.assertNotNull(grammar);
        Assert.assertThat(grammar.getRules().size(), is(1));
    }

    @Test
    public void shouldReturnGrammarWithProperRules() {
        Grammar grammar = OperationGrammarParser.parse(new LoginOperation());
        Rule rule = grammar.getRules().get(0);
        List<Token> tokens = rule.getTokens();
        Assert.assertTrue(tokens.contains(new StringToken("login", "login", true, true)));
        Assert.assertTrue(tokens.contains(new InputToken("CS", "", true, true)));
    }
}