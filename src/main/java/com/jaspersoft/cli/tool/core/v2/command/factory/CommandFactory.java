package com.jaspersoft.cli.tool.core.v2.command.factory;

import com.jaspersoft.cli.tool.core.v2.command.AbstractCommand;
import com.jaspersoft.cli.tool.core.v2.command.Command;
import com.jaspersoft.cli.tool.core.v2.command.impl.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.apache.commons.cli.Option;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple factory that encapsulates command creation. It provides multiple
 * implementations of abstract command.
 *
 * @author Alexander Krasnyanskiy
 */
public class CommandFactory {

    /**
     * Creates command.
     *
     * @param commandName string representation of command type
     * @param session     JRS Rest Client session
     * @param options     command options (keys + values)
     * @return single command
     */
    public static Command create(String commandName, Session session, Option... options) {
        switch (commandName) {
            case "import":
                return new ImportCommand(null, session, options);
            case "export":
                return new ExportCommand(null, session, options);
            case "tree":
                return new TreeCommand(null, session, options);
            case "version":
                return new VersionCommand(null, session, options);
            case "profile":
                return new ProfileCommand(null, session, options);
            default:
                return new HelpCommand(null, null, null);
        }
    }

    /**
     * Creates collection of commands.
     *
     * @param commandNames given names of commands
     * @param session      JRS Rest Client session
     * @param options      command options (keys + values)
     * @return collection of commands
     */
    public static List<Command> create(List<String> commandNames, Session session, Option... options) {
        List<Command> commands = new ArrayList<>();
        for (String commandName : commandNames) {
            commands.add(create(commandName, session, options));
        }
        return commands;
    }

    /**
     * Factory method which creates a concrete command.
     *
     * @param commandClass class of command
     * @param session      JRS Rest Client session
     * @param options      command options (keys + values)
     * @param <T>          type of command
     * @return created instance of Command
     */
    public static <T extends AbstractCommand> T create(Class<T> commandClass, Session session, Option... options) {
        T command = null;
        try {
            command = commandClass.getConstructor(Command.class, Session.class, Option[].class).newInstance(null, session, options); // null is a masterCommand
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return command;
    }
}
