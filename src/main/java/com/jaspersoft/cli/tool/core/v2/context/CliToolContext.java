package com.jaspersoft.cli.tool.core.v2.context;

import com.jaspersoft.cli.tool.core.v2.command.Command;

/**
 * Base interface to provide configuration for application.
 *
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
public interface CliToolContext {

    /**
     * Return a component.
     * @param name a key of the component
     * @return command component
     */
    public Command getCommand(String name);

    /**
     * As the previous method this one returns a component command,
     * but smart lookup to get the command.
     * @param commandClass class of component
     * @param <T> type of command
     * @return command component
     */
    public <T extends Command> T getCommand(Class<T> commandClass);
}
