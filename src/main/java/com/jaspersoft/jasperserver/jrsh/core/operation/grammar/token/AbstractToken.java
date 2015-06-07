package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token;

import jline.console.completer.Completer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Alexander Krasnyanskiy
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class AbstractToken implements Token {

    protected String name;
    protected String value;
    protected boolean mandatory;
    protected boolean tailOfRule;

    @Override
    public abstract Completer getCompleter();

    @Override
    public abstract boolean match(String input);

}
