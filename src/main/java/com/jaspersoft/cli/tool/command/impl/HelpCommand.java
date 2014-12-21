package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import static com.jaspersoft.cli.tool.command.common.JCommanderContext.getInstance;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "help command")
public class HelpCommand extends AbstractCommand<Void> {

    @Parameter(names = {"-c", "--command"})
    private String cmdName;

    public HelpCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        getInstance().usage(cmdName);
        return null;
    }
}
