package com.jaspersoft.jasperserver.shell.completion.completer;

import com.jaspersoft.jasperserver.shell.completion.CompleterUtil;
import com.jaspersoft.jasperserver.shell.completion.ParameterHolder;
import jline.console.completer.StringsCompleter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static jline.internal.Preconditions.checkNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
@Log
@SuppressWarnings("unchecked")
public class CustomParameterCompleter extends StringsCompleter {

    protected List<String> params = new ArrayList<>();

    public CustomParameterCompleter(String... params) {
        this.params.addAll(asList(params));
    }

    public CustomParameterCompleter(Collection<String> params) {
        this.params.addAll(params);
    }

    public CustomParameterCompleter() {
        // NOP
    }

    @Override
    public final int complete(String buf, int cursor, List candidates) {

        for (String s : ParameterHolder.getParams()) {
            if (params.contains(s)) {
                params.remove(s);
            }
        }

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
        String common = CompleterUtil.commonSubstring(buf, params);
        if (common.equals(buf)) {
            candidates.addAll(CompleterUtil.filter(buf, params));
            if (CompleterUtil.belong(buf, params)) {
                candidates.clear();
                candidates.add(" ");
                return cursor;
            }
            return cursor - common.length();
        }
        if (!common.isEmpty()) {
            String diff = CompleterUtil.diff(buf, common);
            if (!diff.isEmpty()) {
                if (CompleterUtil.match(buf, CompleterUtil.filter(buf, params))) {
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
            if (CompleterUtil.belong(buf, params)) {
                candidates.add(" ");
            }
        }
        return cursor;
    }
}
