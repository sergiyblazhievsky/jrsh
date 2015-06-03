package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl;

import com.google.common.io.Files;
import com.jaspersoft.jasperserver.jrsh.core.completion.impl.FileCompleter;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.AbstractToken;
import jline.console.completer.Completer;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * @author Alex Krasnyanskiy
 */
@EqualsAndHashCode(callSuper = true)
public class FileNameToken extends AbstractToken {

    public FileNameToken(String name, String value, boolean mandatory, boolean tailOfRule) {
        super(name, value, mandatory, tailOfRule);
    }

    @Override
    public Completer getCompleter() {
        return new FileCompleter();
    }

    @Override
    public boolean match(String input) {
        Files.isFile().apply(new File(input));
        return true;
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }
}
