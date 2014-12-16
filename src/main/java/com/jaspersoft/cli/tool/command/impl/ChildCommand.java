package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import lombok.Data;

/**
 * This is dummy command for test. Don't use it.
 */
@Data
@Parameters(commandDescription = "dummy command")
@Deprecated
public class ChildCommand extends AbstractCommand<Void>{

    @Parameter(names = {"--dummy"}, required = true)
    private String dummy;

    @Override
    public Void execute() {
        System.out.format("child=%s%n", dummy);
        return null;
    }
}
