package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.factory.SessionFactory;

/**
 * @author Alexander Krasnyanskiy
 */
public class LogoutCommand extends Command {

    @Override
    void run() {
        SessionFactory.getInstance().logout();
    }
}
