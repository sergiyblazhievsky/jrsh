package com.jaspersoft.cli.tool.core.v2.command;

/**
 * @author Alexander Krasnyanskiy
 */
public interface Command<T> {
    T execute();
}
