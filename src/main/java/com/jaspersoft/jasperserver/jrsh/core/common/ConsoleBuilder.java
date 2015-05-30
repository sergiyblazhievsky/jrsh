package com.jaspersoft.jasperserver.jrsh.core.common;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.CompletionHandler;
import jline.console.history.PersistentHistory;

import java.io.IOException;

public class ConsoleBuilder {
    private ConsoleReader console;

    public ConsoleBuilder() {
        try {
            this.console = new ConsoleReader();
        } catch (IOException e) {
            throw new RuntimeException("Could not create JLine console");
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

    public ConsoleBuilder withHistory(PersistentHistory history) {
        console.setHistory(history);
        return this;
    }

    public ConsoleReader build() {
        return console;
    }
}
