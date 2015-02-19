package com.jaspersoft.jasperserver.shell.completion.completer;

import com.jaspersoft.jasperserver.shell.completion.util.ConverterUtil;
import com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil;
import jline.console.completer.Completer;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jline.internal.Preconditions.checkNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
@Log
@Deprecated
public class RepositoryPathCompleter implements Completer {

    public static List<Pair<String, Boolean>> resources;

    public int complete(String buffer, final int cursor, final List<CharSequence> candidates) {
        checkNotNull(candidates);
        if (buffer == null) {
            buffer = "";
        }
        String translated = buffer;
        return matchFiles(translated, resources, candidates);
    }

    protected int matchFiles(String translated, List<Pair<String, Boolean>> resources, List<CharSequence> candidates) {
        if (resources == null) {
            return -1;
        }
        String common = CompleterUtil.commonSubstring(translated, ConverterUtil.convert(resources));
        String diff = CompleterUtil.diff(translated, common);
        if (!diff.isEmpty()) {
            candidates.clear();
            candidates.add(diff);

            // |||||||
            // logging
            // |||||||
            if (candidates.size() > 1) {
                log.info(String.format("o_O: %s", candidates.size()));
            }
            return translated.length();
        }
        Set<String> cuts = new HashSet<>();
        for (Pair<String, Boolean> resource : resources) {
            if (resource.getKey().startsWith(common)) {
                String s = resource.getKey().substring(common.length());
                int idx = s.indexOf("/");
                String cut = idx < 0 ? s : s.substring(0, idx);
                String k = "";
                if (!cut.isEmpty()) {
                    String h = translated;
                    int index = h.lastIndexOf("/");
                    h = h.substring(index);
                    if (h.startsWith("/")) {
                        h = h.substring(1);
                    }
                    k = h + cut;
                }
                cuts.add(k + (idx < 0 ? resource.getValue() ? k.endsWith("/") ? "" : "/" : "" : "/"));
            }
        }
        if (cuts.contains("/") && cuts.size() == 1) {
            if (translated.endsWith("/")) {
                candidates.clear();
                candidates.add(" ");

                // |||||||
                // logging
                // |||||||
                if (candidates.size() > 1) {
                    log.info(String.format("Huh: %s", candidates.size()));
                }
                return translated.length() - 1;
            }
        }
        if (!cuts.isEmpty()) {
            candidates.clear();
            candidates.addAll(cuts);
        }
        String bob = "";
        int i = translated.lastIndexOf('/');
        if (i >= 0) {
            bob = translated.substring(i);
        }
        boolean flag = true;
        for (String cut : cuts) {
            if (!cut.startsWith(bob)) {
                flag = false;
            }
        }
        if (!flag) {
            int idx = translated.lastIndexOf('/');
            String d = idx > 0 ? translated.substring(idx + 1) : translated.substring(idx);
            if (!d.isEmpty()) {
                if (!d.equals("/")) {
                    if (candidates.size() > 1) {
                        if (candidates.contains("/")) {

                            // |||||||
                            // logging
                            // |||||||
                            if (candidates.size() > 1) {
                                log.info(String.format("Ah: %s", candidates.size()));
                            }
                            return translated.length();
                        }
                        if (candidates.contains(d) || candidates.contains("")) {

                            // |||||||
                            // logging
                            // |||||||
                            if (candidates.size() > 1) {
                                log.info(String.format("Oh: %s", candidates.size()));
                            }
                            return translated.length();
                        }

                        // |||||||
                        // logging
                        // |||||||
                        if (candidates.size() > 1) {
                            log.info(String.format("Uh: %s", candidates.size()));
                        }
                        return translated.length() - d.length();
                    }
                }
            }
        }
        if (candidates.isEmpty() || (candidates.size() == 1 && candidates.contains(""))) {
            candidates.clear();
            candidates.add(" ");
        }


        // |||||||
        // logging
        // |||||||
        if (candidates.size() > 1) {
            log.info(String.format("Nah: %s", candidates.size()));
        }
        return translated.length();
    }
}