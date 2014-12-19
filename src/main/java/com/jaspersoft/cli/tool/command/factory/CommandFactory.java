package com.jaspersoft.cli.tool.command.factory;

import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.impl.*;
import com.jaspersoft.cli.tool.exception.IllegalCommandNameException;
import com.jaspersoft.cli.tool.exception.UnsupportedCommandException;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Factory which is responsible for command creation. It provides multiple
 * implementations of command.
 *
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
public class CommandFactory {

    /**
     * Creates a command using command name as a key.
     *
     * @param commandName name of the command
     * @return created command instance
     */
    public static AbstractCommand create(String commandName) {
        switch (commandName) {
            case "jrs":
                return new JrsCommand("jrs", 1);
            case "import":
                return new ImportCommand("import", 2);
            case "show":
                return new ShowCommand("show", 2);
            case "repo":
                return new ShowRepoCommand("repo", 3);
            case "server-info":
                return new ShowServerInfoCommand("server-info", 3);
            case "profile":
                throw new UnsupportedCommandException();
            case "debug":
                throw new UnsupportedCommandException();
            default:
                throw new IllegalCommandNameException();
        }
    }

    /**
     * Creates command map.
     *
     * @param commandNames array of command names
     * @return created command holder map
     */
    public static Map<String, AbstractCommand> create(String... commandNames) {
        Map<String, AbstractCommand> commands = new TreeMap<>();
        for (String cmdName : commandNames) {
            commands.put(cmdName, create(cmdName));
        }
        return commands;
    }
}
