package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl;

import com.jaspersoft.jasperserver.jrsh.core.completion.impl.InputCompleter;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.AbstractToken;
import jline.console.completer.Completer;
import lombok.EqualsAndHashCode;

/**
 * @author Alex Krasnyanskiy
 */
@EqualsAndHashCode(callSuper = true)
public class InputToken extends AbstractToken {

    public InputToken(String name, String value, boolean mandatory, boolean tailOfRule) {
        super(name, value, mandatory, tailOfRule);
    }

    @Override
    public boolean isTailOfRule() {
        return tailOfRule;
    }

    @Override
    public Completer getCompleter() {
        return new InputCompleter();
    }

    @Override
    public boolean match(String input) {
        return true;
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }
}
