package com.jaspersoft.jasperserver.shell.command;

import jline.console.ConsoleReader;

/**
 * @author Alexander Krasnyanskiy
 */
public interface ConsoleReaderAware {
    public void setReader(ConsoleReader consoleReader);
}
