package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl;

import com.jaspersoft.jasperserver.jrsh.core.completion.impl.JrshRepositoryCompleter;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.AbstractToken;
import jline.console.completer.Completer;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class RepositoryToken extends AbstractToken {

    public RepositoryToken(String name, String value, boolean mandatory, boolean tailOfRule) {
        super(name, value, mandatory, tailOfRule);
    }

    @Override
    public Completer getCompleter() {
        return new JrshRepositoryCompleter();
    }

    @Override
    public boolean match(String input) {
        return input.startsWith("/") && !input.endsWith("/");
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }
}
