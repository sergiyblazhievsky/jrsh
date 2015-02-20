package com.jaspersoft.jasperserver.shell.completion.completer;

import com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil;

import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil.commonSubstring;
import static com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil.filter;
import static com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil.match;
import static jline.internal.Preconditions.checkNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReplicateCommandCommandParameterCompleter extends GeneralCommandParameterCompleter {

    public static boolean f = false;
    public static boolean s = false;

    private List<String> params = new ArrayList<>();
    private List<String> copy = new ArrayList<>();
    private final static String direction = "to"; // fixme: delete it!

    public ReplicateCommandCommandParameterCompleter(List<String> params) {
        this.params.addAll(params);
        this.copy.addAll(params);
    }

    public ReplicateCommandCommandParameterCompleter() {
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
            if (f) {
                if (params.isEmpty()) {
                    params.addAll(copy);
                    candidates.remove(direction);
                    if (candidates.isEmpty()) {
                        candidates.addAll(params);
                    }
                } else if (!s) {
                    candidates.clear();
                    params.clear();
                    candidates.add(direction);
                    s = true;
                }
                f = false;
            }
            candidates.addAll(params);
            return cursor;
        } else {
            if (buf.endsWith(" ")) {
                if (f) {
                    candidates.clear();
                    candidates.add(direction);
                    f = false;
                } else {
                    candidates.addAll(params);
                }
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
            if (CompleterUtil.belong(buf, params)) {
                candidates.clear();
                candidates.add(" ");
                f = true;
                return cursor;
            }
            return cursor - common.length();
        }
        if (!common.isEmpty()) {
            String diff = CompleterUtil.diff(buf, common);
            if (!diff.isEmpty()) {
                if (match(buf, filter(buf, params))) {
                    candidates.add(diff + " ");
                    f = true;
                } else {
                    candidates.add(diff);
                }
            } else {
                if (match(buf, params)) {
                    candidates.add(" ");
                    f = true;
                }
            }
        } else if (!buf.endsWith(" ")) {
            candidates.add(" ");
            f = true;
        } else {
            if (CompleterUtil.belong(buf, params)) {
                candidates.add(" ");
                f = true;
            }
        }
        return cursor;
    }
}
