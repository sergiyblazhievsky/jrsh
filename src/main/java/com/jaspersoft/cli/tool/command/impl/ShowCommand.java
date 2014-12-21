package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.exception.UnspecifiedSubcommandException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "show")
public class ShowCommand extends AbstractCommand<Void> {

    //@Parameter(names = "--format", required = false, converter = OptionConverter.class)
    //private OutputFormat format = LIST_TEXT;
    private static boolean isParent;

    public ShowCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        if (!isParent) throw new UnspecifiedSubcommandException();
        return null;
    }

    public static void establishPaternity() {
        isParent = true;
    }
}
