package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.rule;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

import java.util.List;

public interface Rule {

    List<Token> getTokens();

    void addToken(Token token);

}
