package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.exception.UnsupportedCommandException;

@Parameters(commandDescription = "jrs root command")
@Deprecated
public class ExportCommand extends AbstractCommand<Void> {


    public ExportCommand(String commandName, Integer level){
        super(commandName, level);
    }


    @Override
    public Void execute() {
        throw new UnsupportedCommandException();
    }
}