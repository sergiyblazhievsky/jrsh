package com.jaspersoft.jasperserver.jrsh.core.completion.impl;

import jline.console.completer.Completer;

import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class SkipInputCompleter implements Completer {
    @Override
    public int complete(String s, int i, List<CharSequence> list) {
        return 0;
    }
}
