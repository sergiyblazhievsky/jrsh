package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import lombok.Data;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Parameters(commandDescription = "version")
public class ServerInfoCommand extends AbstractCommand<Void> {

    @Parameter(names = "--build", required = false)
    public boolean build;
    @Parameter(names = "--edition", required = false)
    public boolean edition;
    @Parameter(names = "--version", required = false)
    public boolean version;

    @Override
    public Void execute() {
        System.out.format("version: %b build: %b, edition: %b%n", build, edition, version);
        return null;
    }
}

