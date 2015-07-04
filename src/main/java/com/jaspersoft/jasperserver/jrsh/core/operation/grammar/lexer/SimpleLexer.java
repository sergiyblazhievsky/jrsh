package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.lexer;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class SimpleLexer implements Lexer {
    @Override
    public List<String> convert(String line) {
        return Arrays.asList(line.split("\\s+"));
    }
}