package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token;

import jline.console.completer.Completer;

/**
 * Token is a structure representing a lexeme that
 * explicitly indicates its categorization for
 * the purpose of parsing.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public interface Token {

    String getName();

    String getValue();

    boolean isMandatory();

    boolean isTailOfRule();

    Completer getCompleter();

    boolean match(String input);

}
