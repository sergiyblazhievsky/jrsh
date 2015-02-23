package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;

/**
 * @author Alexander Krasnyanskiy
 */
public class VersionCommand extends Command {

    public VersionCommand() {
        name = "version";
        description = "Show JRSH version and paths.";
        usageDescription = "\tUsage:  jrsh version";
    }

    @Override
    public void run() {
        // TODO: implement me!
    }
}