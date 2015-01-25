package com.jaspersoft.jasperserver.shell.command;

import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileCommand extends Command {


    public ProfileCommand() {
        name = "profile";
        description = "Show current profile information.";
    }

    @Override
    void run() {
        if (profile.getUrl() == null && profile.getUsername() == null) { // mandatory profile properties
            System.out.println("Not available.");
        } else {
            out.printf("\nprofile name:\t%s\nserver url:\t\t%s\nusername:\t\t%s\norganization:\t%s\n\n",
                    profile.getName(), profile.getUrl(), profile.getUsername(), profile.getOrganization());
        }
    }
}
