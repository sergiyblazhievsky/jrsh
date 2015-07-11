package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl;

import com.jaspersoft.jasperserver.jrsh.core.completion.impl.SkippedInputCompleter;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.AbstractToken;
import jline.console.completer.Completer;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class SkipInputToken extends AbstractToken {

    public SkipInputToken(String name, String value, boolean mandatory, boolean tailOfRule) {
        super(name, value, mandatory, tailOfRule);
    }

    @Override
    public boolean isTailOfRule() {
        return tailOfRule;
    }

    @Override
    public Completer getCompleter() {
        return new SkippedInputCompleter();
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
