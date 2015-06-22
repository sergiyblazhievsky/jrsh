package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to convert a sequence of characters
 * into a sequence of tokens.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public interface Lexer {

    /**
     * Convert characters into tokens.
     *
     * @param line characters
     * @return stringified tokens
     */
    List<String> convert(String line);

    /**
     * A naive implementation of {@link Lexer}
     * interface.
     */
    class DefaultLexer implements Lexer {
        @Override
        public List<String> convert(String line) {
            return Arrays.asList(line.split("\\s+"));
        }
    }
}
