package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.common.OptionConverter;
import lombok.Data;

import static com.jaspersoft.cli.tool.command.impl.ShowCommand.OutputFormat.LIST_TEXT;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Parameters(commandDescription = "show")
public class ShowCommand extends AbstractCommand<Void> {
    @Parameter(names = "--format", required = false, converter = OptionConverter.class)
    private OutputFormat format = LIST_TEXT;

    public ShowCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        return null;
    }

    public enum OutputFormat {
        JSON, TEXT, LIST_TEXT
    }
}
