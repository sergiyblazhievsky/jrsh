package com.jaspersoft.cli.tool.command.factory;

import com.jaspersoft.cli.tool.command.Command;
import com.jaspersoft.cli.tool.command.impl.ImportCommand;
import com.jaspersoft.cli.tool.command.impl.JrsCommand;
import com.jaspersoft.cli.tool.command.impl.ShowCommand;
import com.jaspersoft.cli.tool.command.impl.ServerInfoCommand;
import com.jaspersoft.cli.tool.exception.IllegalCommandNameException;

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
    public static Command create(String commandName) {
        switch (commandName) {
            case "jrs":
                return new JrsCommand().setLevel(1);
            case "import":
                return new ImportCommand().setLevel(2);
            case "show":
                return new ShowCommand().setLevel(2);
            case "info":
                return new ServerInfoCommand().setLevel(2);
            // fixme: delete these dummy commands
//            case "apply":
//                return new ApplyCommand();
//            case "revert":
//                return new RevertCommand();
//            case "child":
//                return new ChildCommand();
//            case "export":
//                return new DummyExportCommand();
            // todo: implement them 8>
//            case "profile":
//                throw new UnimplementedCommandException();
            default:
                throw new IllegalCommandNameException();
        }
    }

    /**
     * Creates a map of commands.
     *
     * @param commandNames collection of command names
     * @return created command holder map
     */
    public static Map<String, Command> create(Collection<String> commandNames) {
        Map<String, Command> commands = new TreeMap<>();
        for (String cmdName : commandNames) {
            commands.put(cmdName, create(cmdName));
        }
        return commands;
    }
}
