package com.jaspersoft.jasperserver.shell.command;

import java.io.IOException;

/**
 * @author Alexander Krasnyanskiy
 */
public class ClearScreenCommand extends Command {

    public ClearScreenCommand() {
        name = "clear";
        description = "Clear screen.";
    }

    @Override
    void run() {
        try {
            reader.clearScreen();
        } catch (IOException ignored) {
            // NOP
        }
    }
}
