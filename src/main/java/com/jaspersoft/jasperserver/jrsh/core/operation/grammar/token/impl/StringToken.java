package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.AbstractToken;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import lombok.EqualsAndHashCode;

/**
 * @author Alex Krasnyanskiy
 */
@EqualsAndHashCode(callSuper = true)
public class StringToken extends AbstractToken {

    public StringToken(String name, String value, boolean mandatory, boolean tailOfRule) {
        super(name, value, mandatory, tailOfRule);
    }

    @Override
    public boolean isTailOfRule() {
        return tailOfRule;
    }

    @Override
    public Completer getCompleter() {
        return new StringsCompleter(value);
    }

    @Override
    public boolean match(String input) {
        return input.equals(value);
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }
}
