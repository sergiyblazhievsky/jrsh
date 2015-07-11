package com.jaspersoft.jasperserver.jrsh.core.completion.impl;

import jline.console.completer.Completer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static jline.internal.Preconditions.checkNotNull;

public class JrshStringCompleter implements Completer {
    private final SortedSet<String> strings = new TreeSet<String>();

    public JrshStringCompleter() {
        // empty
    }

    public JrshStringCompleter(final Collection<String> strings) {
        checkNotNull(strings);
        getStrings().addAll(strings);
    }

    public JrshStringCompleter(final String... strings) {
        this(Arrays.asList(strings));
    }

    public Collection<String> getStrings() {
        return strings;
    }

    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        // buffer could be null
        checkNotNull(candidates);

        if (buffer != null && cursor < buffer.length()) {
            candidates.add("");
            return buffer.length();
        }

        if (buffer == null) {
            candidates.addAll(strings);
        } else {
            for (String match : strings.tailSet(buffer)) {
                if (!match.startsWith(buffer)) {
                    break;
                }

                candidates.add(match);
            }
        }

        if (candidates.size() == 1) {
            candidates.set(0, candidates.get(0) + " ");
        }

        return candidates.isEmpty() ? -1 : 0;
    }
}
