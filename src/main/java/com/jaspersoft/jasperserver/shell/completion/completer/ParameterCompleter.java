package com.jaspersoft.jasperserver.shell.completion.completer;

import com.jaspersoft.jasperserver.shell.completion.ParameterHolder;
import jline.console.completer.StringsCompleter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.completion.CompleterUtil.belong;
import static com.jaspersoft.jasperserver.shell.completion.CompleterUtil.commonSubstring;
import static com.jaspersoft.jasperserver.shell.completion.CompleterUtil.diff;
import static com.jaspersoft.jasperserver.shell.completion.CompleterUtil.filter;
import static com.jaspersoft.jasperserver.shell.completion.CompleterUtil.match;
import static java.util.Arrays.asList;
import static jline.internal.Preconditions.checkNotNull;

/**
 * Completer for a set of command parameter.
 *
 * @author Alexander Krasnyanskiy
 */
@Log
@SuppressWarnings("unchecked")
public class ParameterCompleter extends StringsCompleter {

    protected List<String> params = new ArrayList<>();

    public ParameterCompleter(String... params) {
        this.params.addAll(asList(params));
    }

    public ParameterCompleter(Collection<String> params) {
        this.params.addAll(params);
    }

    public ParameterCompleter() {
        // empty
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
    public final int complete(String buf, int cursor, List candidates) {
        checkNotNull(candidates);
        if (buf == null) {
            candidates.addAll(params);
            return cursor;
        } else {
            if (buf.endsWith(" ")) {
                return cursor;
            }
            String[] divided = buf.split("\\s+");
            if (divided.length > 1) {
                buf = divided[divided.length - 1];
            }
        }
        String common = commonSubstring(buf, params);
        if (common.equals(buf)) {
            candidates.addAll(filter(buf, params));
            if (belong(buf, params)) {
                candidates.clear();

                ParameterHolder.getParams().add(buf.trim()); // gotcha!

                candidates.add(" ");
                return cursor;
            }
            return cursor - common.length();
        }
        if (!common.isEmpty()) {
            String diff = diff(buf, common);
            if (!diff.isEmpty()) {
                if (match(buf, filter(buf, params))) {
                    candidates.add(diff + " ");
                } else {
                    candidates.add(diff);
                }
            } else {
                if (match(buf, params)) {
                    candidates.add(" ");
                }
            }
        } else if (!buf.endsWith(" ")) {
            candidates.add(" ");
        } else {
            if (belong(buf, params)) {
                candidates.add(" ");
            }
        }
        return cursor;
    }
}