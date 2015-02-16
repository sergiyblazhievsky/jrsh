package com.jaspersoft.jasperserver.shell.completion.completer;

import com.jaspersoft.jasperserver.shell.completion.CompleterUtil;
import jline.console.completer.Completer;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jline.internal.Preconditions.checkNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
@Log
public class RepositoryPathCompleter implements Completer {

    public static List<String> resources;
    //private static int counter = 0;

    public int complete(String buffer, final int cursor, final List<CharSequence> candidates) {
        //log.info(String.format("[%s - %d - %d]", buffer, cursor, candidates.size()));
        checkNotNull(candidates);
        if (buffer == null) {
            buffer = "";
        }
        String translated = buffer;
        return matchFiles(translated, resources, candidates);
    }

    protected int matchFiles(String translated, List<String> resources, List<CharSequence> candidates) {
        if (resources == null) {
            return -1;
        }
        for (String resource : resources) {
            if (resource.startsWith(translated)) {
                String name = resource + " ";
                candidates.add(name);
                //log.info(format("\n::> translated %s\n::> resource %s", translated, resource));
            }
        }

        String common = CompleterUtil.commonSubstring(translated, resources);
        String diff = CompleterUtil.diff(translated, common);
        if (!diff.isEmpty()) {
            candidates.clear();
            candidates.add(diff);
            return translated.length();
        }

        //log.info(format("\n %d common -> %s", counter, common));
        //log.info(format("\n %d diff -> %s", counter, diff));

        Set<String> cuts = new HashSet<>();

        for (String r : resources) {
            if (r.startsWith(common)){
                String s = r.substring(common.length());
                int idx = s.indexOf("/");
                String cut = idx < 0 ? s : s.substring(0, idx);
                cuts.add(cut + (idx < 0 ? "" : "/"));
            }
        }

        if (!cuts.isEmpty()) {
            candidates.clear();
            candidates.addAll(cuts);
        }

        //log.info(format("\n %d candidates: %s\n", counter++, cuts));

        return /*0*/translated.length();
    }

}
