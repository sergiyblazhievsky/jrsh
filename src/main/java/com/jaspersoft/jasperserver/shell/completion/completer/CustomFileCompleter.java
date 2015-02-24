package com.jaspersoft.jasperserver.shell.completion.completer;

import jline.console.completer.Completer;
import jline.internal.Configuration;

import java.io.File;
import java.util.List;

import static jline.internal.Preconditions.checkNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class CustomFileCompleter implements Completer {


    public int complete(String buffer, final int cursor, final List<CharSequence> candidates) {
        checkNotNull(candidates);

        if (buffer == null) {
            buffer = "";
        }

        String translated = buffer;
        File homeDir = getUserHome();

        if (translated.startsWith("~" + separator())) {
            translated = homeDir.getPath() + translated.substring(1);
        }
        else if (translated.startsWith("~")) {
            translated = homeDir.getParentFile().getAbsolutePath();
        }
        else if (!(new File(translated).isAbsolute())) {
            String cwd = getUserDir().getAbsolutePath();
            translated = cwd + separator() + translated;
        }

        File file = new File(translated);
        final File dir;

        if (translated.endsWith(separator())) {
            dir = file;
        } else {
            dir = file.getParentFile();
        }

        File[] entries = dir == null ? new File[0] : dir.listFiles();

        return matchFiles(buffer, translated, entries, candidates);
    }

    protected String separator() {
        return File.separator;
    }

    protected File getUserHome() {
        return Configuration.getUserHome();
    }

    protected File getUserDir() {
        return new File(".");
    }

    protected int matchFiles(final String buffer, final String translated, final File[] files, final List<CharSequence> candidates) {
        if (files == null) {
            return -1;
        }

        int matches = 0;

        for (File file : files) {
            if (file.getAbsolutePath().startsWith(translated)) {
                matches++;
            }
        }
        for (File file : files) {
            if (file.getAbsolutePath().startsWith(translated)) {
                CharSequence name = file.getName() + (matches == 1 && file.isDirectory() ? separator() : " ");
                candidates.add(render(file, name).toString());
            }
        }

        // todo! diff
        if (matches == 0 && candidates.isEmpty()){
            candidates.add(""); // was " " wait for space from user
            return buffer.length();
        }

        final int index = buffer.lastIndexOf(separator());

        return index + separator().length();
    }

    protected CharSequence render(final File file, final CharSequence name) {
        return name;
    }
}