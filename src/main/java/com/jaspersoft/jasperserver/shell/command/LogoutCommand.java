package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.factory.SessionFactory;

import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class LogoutCommand extends Command {

    @Override
    void run() {
        SessionFactory.getInstance().logout();
        out.println("You've logged out.");
    }
}
