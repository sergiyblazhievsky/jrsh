package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "help command")
public class HelpCommand extends AbstractCommand<Void> {

    public HelpCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        //__.usage();
        return null;
    }
}
