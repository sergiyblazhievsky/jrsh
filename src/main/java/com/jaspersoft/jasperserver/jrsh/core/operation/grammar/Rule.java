package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Production rule is used to parse user input into
 * a fully configured operation.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public interface Rule {

    List<Token> getTokens();

    void addToken(Token token);

    /**
     * Naive implementation.
     */
    class DefaultRule implements Rule {
        private List<Token> tokens;

        public DefaultRule() {
            tokens = new ArrayList<Token>();
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
