package com.jaspersoft.jasperserver.jrsh.core.common;

import com.jaspersoft.jasperserver.jrsh.core.common.exception.CouldNotCreateJLineConsoleException;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.CompletionHandler;

import java.io.IOException;

public class ConsoleBuilder {
    private ConsoleReader console;

    public ConsoleBuilder() {
        try {
            this.console = new ConsoleReader();
        } catch (IOException e) {
            throw new CouldNotCreateJLineConsoleException();
        }
    }

    public ConsoleBuilder withPrompt(String prompt) {
        console.setPrompt(prompt);
        return this;
    }

    public ConsoleBuilder withCompleter(Completer completer) {
        console.addCompleter(completer);
        return this;
    }

    public ConsoleBuilder withHandler(CompletionHandler handler) {
        console.setCompletionHandler(handler);
        return this;
    }

    public ConsoleBuilder withInterruptHandling() {
        console.setHandleUserInterrupt(true);
        return this;
    }

    /*
    public ConsoleBuilder withHistory(PersistentHistory history) {
        console.setHistory(history);
        return this;
    }
    */

    public ConsoleReader build() {
        return console;
    }
}
