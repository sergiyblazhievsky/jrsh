package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.exception.UnimplementedCommandException;

/**
 *
 */
@Parameters(commandDescription = "jrs root command")
@Deprecated
public class DummyExportCommand extends AbstractCommand<Void> {
    @Override
    public Void execute() {
        throw new UnimplementedCommandException();
    }
}