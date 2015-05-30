package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoGrammarRulesFoundException;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConditionsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowAnExceptionIfOperationIsNull() {
        thrown.expect(NoOperationFoundException.class);
        Conditions.checkOperation(null);
    }

    @Test
    public void shouldThrowAnExceptionIfMatchedRuleDoesNotExist() {
        thrown.expect(NoGrammarRulesFoundException.class);
        Conditions.checkMatchedRulesFlag(false);
    }

}