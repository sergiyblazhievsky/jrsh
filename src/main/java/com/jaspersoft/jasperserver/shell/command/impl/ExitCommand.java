package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.getInstance;
import static java.lang.System.exit;

/**
 * @author Alexander Krasnyanskiy
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        name = "exit";
        description = "Exit from the application.";
    }

    @Override
    public void run() {
        try {
            getInstance().logout();
        } catch (Exception ignored) {/* NOP */} finally {
            exit(0);
        }
    }
}
