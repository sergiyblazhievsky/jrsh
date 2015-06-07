package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token;

import jline.console.completer.Completer;

/**
 * @author Alexander Krasnyanskiy
 */
public interface Token {

    String getName();

    String getValue();

    boolean isMandatory();

    boolean isTailOfRule();

    Completer getCompleter();

    boolean match(String input);

}
