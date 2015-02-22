package com.jaspersoft.jasperserver.shell.completion.completer;

import jline.console.completer.StringsCompleter;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import static jline.internal.Preconditions.checkNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class ColoredStringsCompleter extends StringsCompleter {

    public ColoredStringsCompleter(final Map<String, Color> options) {
        super();
    }

    public ColoredStringsCompleter(final String... strings) {
        super(strings);
    }

    public static enum Color {
        RED,
        GREEN,
        YELLOW
    }

    @Override
    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        checkNotNull(candidates);
        if (buffer == null) {
            candidates.addAll(getStrings());
        } else {
            for (String match : ((SortedSet<String>) getStrings()).tailSet(buffer)) {
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

    private static String[] convert(final Color color, final String... strings) {
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            switch (color) {
                case RED:
                    replace(str, 31, strings, i);
                    break;
                case YELLOW:
                    replace(str, 33, strings, i);
                    break;
                case GREEN:
                    replace(str, 32, strings, i);
                    break;
            }
        }
        return strings;
    }
    private static void replace(final String src, final Integer code, final String[] strings, final Integer index) {
        if (src.startsWith("\u001B[" + code + "m") && src.endsWith("\u001B[0m")) {
            strings[index] = src.replace("\u001B[" + code + "m", "").replace("\u001B[0m", "");
        }
    }
}
