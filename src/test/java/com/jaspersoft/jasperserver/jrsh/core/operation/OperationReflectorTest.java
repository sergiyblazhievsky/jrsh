package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.InputToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.RepositoryToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.ExportOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class OperationReflectorTest {

    @Test
    public void shouldSetFieldsOfLoginOperationInstance() {
        // Given
        LoginOperation loginOperation = new LoginOperation();
        List<Token> ruleTokens = Arrays.<Token>asList(
                new StringToken("login", "login", true, false),
                new InputToken("CS", "", true, true)
        );
        List<String> inputTokens = Arrays.asList("login", "superuser%superuser@localhost:8080/jasperserver-pro");

        //When
        OperationReflector.set(loginOperation, ruleTokens, inputTokens);

        // Then
        Assert.assertEquals("superuser%superuser@localhost:8080/jasperserver-pro", loginOperation.getConnectionString());
    }

    @Test
    public void shouldSetFieldsOfExportOperationInstance() {
        // Given
        ExportOperation loginOperation = new ExportOperation();
        List<Token> ruleTokens = Arrays.<Token>asList(
                new StringToken("export", "export", true, false),
                new StringToken("RE", "repository", true, false),
                new RepositoryToken("RP", "", true, true)
        );
        List<String> inputTokens = Arrays.asList("export", "repository", "/public");

        // When
        OperationReflector.set(loginOperation, ruleTokens, inputTokens);

        // Then
        Assert.assertEquals("repository", loginOperation.getContext());
        Assert.assertEquals("/public", loginOperation.getRepositoryPath());
    }
}