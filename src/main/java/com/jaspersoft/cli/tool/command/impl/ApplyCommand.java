package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import lombok.Data;

/**
 * This is dummy command for test. Don't use it.
 */
@Data
@Parameters(commandDescription = "apply")
@Deprecated
public class ApplyCommand extends AbstractCommand<Void> {

    @Parameter(names = {"--way", "-w"})
    public String way;

    @Override
    public Void execute() {
        System.out.format("way = %s%n", way);
        return null;
    }
}