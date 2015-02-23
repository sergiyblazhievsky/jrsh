package com.jaspersoft.jasperserver.shell;

import jline.Terminal;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * @author Alexander Krasnyanskiy
 */
public class CustomConsoleReader extends ConsoleReader {

    public CustomConsoleReader() throws IOException {
    }

    public CustomConsoleReader(InputStream in, OutputStream out) throws IOException {
        super(in, out);
    }

    public CustomConsoleReader(InputStream in, OutputStream out, Terminal term) throws IOException {
        super(in, out, term);
    }

    public CustomConsoleReader(String appName, InputStream in, OutputStream out, Terminal term) throws IOException {
        super(appName, in, out, term);
    }

    public CustomConsoleReader(String appName, InputStream in, OutputStream out, Terminal term, String encoding) throws IOException {
        super(appName, in, out, term, encoding);
    }

    /**
     * Prints candidates in rows. One candidate per line.
     *
     * @param candidates given candidates
     * @throws IOException
     * @since 1.0RC5
     */
    public void printRows(Collection<CharSequence> candidates) throws IOException {
        for (CharSequence candidate : candidates) {
            print(candidate + "\n");
        }
    }
}
