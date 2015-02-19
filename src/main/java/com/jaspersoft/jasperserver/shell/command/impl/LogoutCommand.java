package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;

import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class LogoutCommand extends Command {

    public LogoutCommand() {
        description = "Logout from JRS.";
    }

    @Override
    public void run() {
        SessionFactory.getInstance().logout();
        out.println("You've logged out.");
    }
}
