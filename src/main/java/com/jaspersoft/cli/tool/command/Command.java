package com.jaspersoft.cli.tool.command;

/**
 * Main interface which is represents a single command of CLI tool.
 *
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
public interface Command<T> {

    /**
     * Business logic of the command.
     *
     * @return specified generic type (usually if command
     * doesn't return a value you can return <code>Void</code> type)
     */
    public T execute();
}
