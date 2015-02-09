package com.jaspersoft.jasperserver.shell.completion;

import jline.console.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.completion.CompleterUtil.filter;
import static jline.internal.Preconditions.checkNotNull;

/**
 * Completer for a set of command parameter.
 *
 * @author Alexander Krasnyanskiy
 */
public class GeneralCommandParameterCompleter extends StringsCompleter {

    private List<String> params = new ArrayList<>();

    public GeneralCommandParameterCompleter(List<String> params) {
        this.params.addAll(params);
    }

    public GeneralCommandParameterCompleter() {
        // NOP
    }

    /**
     * Populates candidates with a list of possible completions for the buffer.
     *
     * @param buf        the buffer
     * @param cursor     current position of the cursor in the buffer
     * @param candidates The collection of candidates to populate
     * @return The index of the buffer for which the completion will be relative
     */
    @Override
    @SuppressWarnings("unchecked")
    public int complete(String buf, int cursor, List candidates) {
        checkNotNull(candidates);
        if (buf == null) {
            candidates.addAll(params);
            return cursor;
        } else {
            if (buf.endsWith(" ")) {
                candidates.addAll(params);
                return cursor;
            }
            String[] divided = buf.split("\\s+");
            if (divided.length > 1) {
                buf = divided[divided.length - 1];
            }
        }
        String common = CompleterUtil.commonSubstring(buf, params);
        if (common.equals(buf)) {
            candidates.addAll(filter(buf, params));
            return cursor - common.length(); // todo: be careful here!
        }
        if (!common.isEmpty()) {
            String diff = CompleterUtil.diff(buf, common);
            if (!diff.isEmpty()) {
                if (CompleterUtil.match(buf, filter(buf, params))) {
                    candidates.add(diff + " ");
                } else {
                    candidates.add(diff);
                }
            } else {
                if (CompleterUtil.match(buf, params)) {
                    candidates.add(" ");
                }
            }
        } else if (!buf.endsWith(" ")) {
            candidates.add(" ");
        } else {
            candidates.addAll(params);
        }
        return cursor;
    }
}