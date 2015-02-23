package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;

/**
 * @author Alexander Krasnyanskiy
 */
public class VersionCommand extends Command {

    public VersionCommand() {
        name = "show";
        description = "Show JRSH version information.";
        usageDescription = "\tUsage:  jrsh version";
    }

    @Override
    public void run() {
        System.out.println("Version");
        System.out.println("Path1");
        System.out.println("Path2");
        System.out.println("Path3");
    }
}
