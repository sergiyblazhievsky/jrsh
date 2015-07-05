package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.lexer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class PathIdentifyingLexer implements Lexer {

    /**
     * Converts a line into tokens considering spaces
     * in file path.
     *
     * Example: $> export repository "/public" to "/Users/alex/My\ folder/file.zip"
     *
     * @param line characters
     * @return tokens
     */
    @Override
    public List<String> convert(String line) {
        ArrayList<String> tokens = new ArrayList<String>();
        String[] parts = line.split("\\s+");

        String word = "";

        for (String part : parts) {
            if (part.endsWith("\\")) {
                word = word.concat(part).concat(" ");
            } else {
                if (word.isEmpty()) {
                    tokens.add(part);
                } else {
                    word = word.concat(part);
                    tokens.add(word);
                    word = "";
                }
            }
        }
        return tokens;
    }
}
