package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;

import static com.jaspersoft.jasperserver.shell.SessionFactory.getInstance;
import static com.jaspersoft.jasperserver.shell.SessionFactory.invalidate;
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
        getInstance().logout();
        invalidate();
        //RepositoryPathCompleter.resources.clear();
        //FolderRepositoryPathCompleter.resources.clear();
        out.println("You've logged out.");
    }
}
