package com.jaspersoft.cli.tool.command.common;

import com.jaspersoft.cli.tool.command.Command;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
public class CommandProcessor {

    /**
     * Executes a batch of commands.
     *
     * @param executableCommandQueue command storage
     */
    public void processInOrder(Map<String, Command> executableCommandQueue) {
        Collection<Command> commands = executableCommandQueue.values();
        TreeSet<Command> set = new TreeSet<>(commands);
        for (Command command : set) {
            command.execute();
        }
    }
}
