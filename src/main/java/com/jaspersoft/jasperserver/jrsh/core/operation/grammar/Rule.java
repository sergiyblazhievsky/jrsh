package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Krasnyanskiy
 */
public interface Rule {

    List<Token> getTokens();

    void addToken(Token token);

    /**
     * Default implementation of {@link Rule} interface.
     */
    class DefaultRule implements Rule {
        private List<Token> tokens;

        public DefaultRule() {
            tokens = new ArrayList<>();
        }

        @Override
        public List<Token> getTokens() {
            return tokens;
        }

        @Override
        public void addToken(Token token) {
            tokens.add(token);
        }

        @Override
        public String toString() {
            return tokens.toString();
        }
    }
}
