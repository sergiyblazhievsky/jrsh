package com.jaspersoft.cli.tool.command.common;

import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.Command;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

import static java.lang.System.exit;

/**
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
@Log4j
public class CommandProcessor {

    /**
     * Executes a batch of commands.
     * @param executableCommandQueue command storage
     */
    public void process(Map<String, AbstractCommand> executableCommandQueue) {
        try {
            Collection<AbstractCommand> commands = executableCommandQueue.values();
            TreeSet<AbstractCommand> set = new TreeSet<>(commands);
            for (Command command : set) {
                command.execute();
            }
            exit(0);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            exit(1); // or > log.error(e.getMessage());
        }
    }
}
