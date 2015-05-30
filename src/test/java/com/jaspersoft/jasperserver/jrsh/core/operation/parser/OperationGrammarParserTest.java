package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import org.junit.Assert;
import org.junit.Test;

public class OperationGrammarParserTest {

    @Test
    public void shouldParseGrammar() {
        Grammar grammar = OperationGrammarParser.parse(new LoginOperation());
        Assert.assertNotNull(grammar);
        Assert.assertEquals(1, grammar.getRules().size());
    }
}