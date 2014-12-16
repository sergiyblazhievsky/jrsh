package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import lombok.Data;

/**
 * This is dummy command for test. Don't use it.
 */
@Data
@Parameters(commandDescription = "back")
@Deprecated
public class RevertCommand extends AbstractCommand<Void> {

    @Parameter(names = "--backPath")
    public String backPath;

    @Override
    public Void execute() {
        System.out.println("back");
        return null;
    }
}