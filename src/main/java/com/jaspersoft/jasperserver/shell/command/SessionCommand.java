package com.jaspersoft.jasperserver.shell.command;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.uptime;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class SessionCommand extends Command {

    public SessionCommand() {
        name = "session";
        description = "Show session parameters plus uptime.";
    }

    @Override
    void run() {
        if (profile.getUrl() == null && profile.getUsername() == null) {
            out.println("There's no active session.");
        } else {
            out.printf("\nserver url:\t\t%s\nusername:\t\t%s\norganization:\t%s\nsession uptime:\t%s\n\n",
                    profile.getUrl() == null ? "unknown" : profile.getUrl(),
                    profile.getUsername() == null ? "unknown" : profile.getUsername(),
                    profile.getOrganization() == null ? "unknown" : profile.getOrganization(),
                    uptime());
        }
    }
}
