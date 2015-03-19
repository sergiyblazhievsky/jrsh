package com.jaspersoft.jasperserver.shell.completion.completer;

import com.jaspersoft.jasperserver.shell.completion.misc.RepositoryContentReceiver;
import com.jaspersoft.jasperserver.shell.completion.misc.Response;
import jline.console.completer.Completer;

import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.shell.completion.util.ResourcesUtil.extract;
import static com.jaspersoft.jasperserver.shell.completion.util.ResourcesUtil.filter;
import static com.jaspersoft.jasperserver.shell.completion.util.ResourcesUtil.normalize;
import static com.jaspersoft.jasperserver.shell.completion.util.ResourcesUtil.process;

/**
 * @author Alexander Krasnyanskiy
 */
//@Log4j
public class PathCompleter implements Completer {

    private RepositoryContentReceiver receiver;

    public PathCompleter() {
        this.receiver = new RepositoryContentReceiver();
    }

    @Override
    public int complete(final String path, final int cursor, final List<CharSequence> candidates) {

        if (!receiver.isSessionAcceable()) {
            return path == null ? -1 : path.length();
        }

        Response resp;
        List<String> filtered;
        List<String> processedResources;
        Map<String, Boolean> resources;


        if (path == null) {
            candidates.add("/");
            return 0;
        }


        if (cursor < path.length()) { // don't move the cursor back and try to complete the input
            return path.length();
        }

        String lastToken = path;
        final int inputLength = lastToken.length();

        lastToken = normalize(lastToken);
        final String extracted = extract(lastToken);
        final int cutLen = extracted.length();
        resp = receiver.receive(lastToken);
        resources = resp.getLookups();
        processedResources = process(resources);
        filtered = filter(extracted, processedResources);

        // may occur when you enter the wrong path
        if (resp.isSuccess() && !path.endsWith("/")) { // to prevent NPE when press [TAB] after >>> export
            candidates.clear();
            candidates.add("/");
            return path.length();
        }

        // filtered candidates not empty
        if (!filtered.isEmpty()) {
            String resourceName = filtered.get(0);
            if (filtered.size() == 1 && !resourceName.endsWith("/")
                    && extracted.endsWith(resourceName)) {
                candidates.clear();
                candidates.add(" ");
                return inputLength;
            }
            if (filtered.size() == 1) {
                candidates.addAll(filtered);
                return inputLength - cutLen;
            }
            candidates.addAll(filtered);
            return inputLength - cutLen;
        } else {
            // prevent print candidates for previous folder if wrong resources name been entered
            // Example /public/Samples/XXXZZZ[TAB] => don't print hints
            if (!path.endsWith("/")) {
                candidates.clear();
            } else {
                // usual case
                candidates.addAll(processedResources);
            }
        }
        return inputLength;
    }
}
