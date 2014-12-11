package com.jaspersoft.cli.tool.core.command;

/**
 * @author Alexander Krasnyanskiy
 */
public class CommandFactory {
    public static Command create(String commandName) {
        switch (commandName) {
            case "import":
                return new ImportCommand();
            case "tree":
                return new TreeCommand();
            default:
                return new HelpCommand();
        }
    }
}
