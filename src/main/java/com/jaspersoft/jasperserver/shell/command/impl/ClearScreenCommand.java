package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;

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
    public void run() {
        try {
            reader.clearScreen();
        } catch (IOException ignored) {
            // NOP
        }
    }
}
