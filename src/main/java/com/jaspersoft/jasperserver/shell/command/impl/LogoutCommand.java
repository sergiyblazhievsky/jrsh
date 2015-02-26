package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.completion.completer.FolderRepositoryPathCompleter;
import com.jaspersoft.jasperserver.shell.completion.completer.RepositoryPathCompleter;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.getInstance;
import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.invalidate;
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
        RepositoryPathCompleter.resources.clear();
        FolderRepositoryPathCompleter.resources.clear();
        out.println("You've logged out.");
    }
}
