package com.jaspersoft.cli.tool.command.factory;

import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.Command;
import com.jaspersoft.cli.tool.command.impl.*;
import com.jaspersoft.cli.tool.exception.IllegalCommandNameException;
import com.jaspersoft.cli.tool.exception.UnimplementedCommandException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
                return new JrsCommand();
            case "import":
                return new ImportCommand();
            case "list":
                return new ListCommand();
            case "info":
                return new ServerInfoCommand();
            // fixme: delete these dummy commands
            case "apply":
                return new ApplyCommand();
            case "revert":
                return new RevertCommand();
            case "child":
                return new ChildCommand();
            case "export":
                return new DummyExportCommand();
            // todo: implement them 8>
            case "profile":
                throw new UnimplementedCommandException();
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

    /**
     * Method which creates a concrete command using Reflection API. Please notice
     * that Reflection API has a few drawbacks:
     * <p/>
     * 1. Performance Overhead
     * Because reflection involves types that are dynamically resolved,
     * certain Java virtual machine optimizations can not be performed.
     * Consequently, reflective operations have slower performance than
     * their non-reflective counterparts, and should be avoided in
     * sections of code which are called frequently in
     * performance-sensitive applications.
     * <p/>
     * 2. Security Restrictions
     * Reflection requires a runtime permission which may not be present
     * when running under a security manager. This is in an important
     * consideration for code which has to run in a restricted security
     * context, such as in an Applet.
     * <p/>
     * 3. Exposure of Internals
     * Since reflection allows code to perform operations that would
     * be illegal in non-reflective code, such as accessing private
     * fields and methods, the use of reflection can result in
     * unexpected side-effects, which may render code dysfunctional
     * and may destroy portability. Reflective code breaks abstractions
     * and therefore may change behavior with upgrades of the platform.
     * <p/>
     * For more info please consider this good article:
     * <href>http://docs.oracle.com/javase/tutorial/reflect/index.html</href>
     *
     * @param commandClass class of the command
     * @param <T>          type of command
     * @return new instance of Command
     */
    public static <T extends AbstractCommand> T create(Class<T> commandClass) {
        T command = null;
        try {
            command = commandClass.getConstructor(Command.class).newInstance();
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return command;
    }

}
