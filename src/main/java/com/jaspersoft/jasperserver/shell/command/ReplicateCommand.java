package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.parameter.Parameter;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReplicateCommand extends Command {

    public ReplicateCommand() {
        name = "replicate";
        description = "Replicate JRS configuration from one JRS to another.\n" +
                "\t\t\t\t Please notice that you should load your profile configuration first.";
        usageDescription = "\tUsage: replicate <src-profile-name> to <dest-profile-name>";
        parameters.add(new Parameter().setName("anonymous").setMultiple(true).setOptional(true));
        parameters.add(new Parameter().setName("to"));
    }

    @Override
    void run() {

    }
}
