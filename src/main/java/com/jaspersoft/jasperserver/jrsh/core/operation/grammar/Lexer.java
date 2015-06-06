package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public interface Lexer {

    List<String> getTokens(String line);

    class DefaultLexer implements Lexer {
        @Override
        public List<String> getTokens(String line) {
            return Arrays.asList(line.split("\\s+"));
        }
    }
}
