package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token;

import jline.console.completer.Completer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class AbstractToken implements Token {

    protected String name;
    protected String value;
    protected boolean mandatory;
    protected boolean tailOfRule;

    public abstract Completer getCompleter();
    public abstract boolean match(String input);

}
