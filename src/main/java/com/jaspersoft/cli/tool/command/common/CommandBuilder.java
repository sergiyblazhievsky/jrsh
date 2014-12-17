package com.jaspersoft.cli.tool.command.common;

import com.beust.jcommander.JCommander;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.Command;
import com.jaspersoft.cli.tool.command.factory.CommandFactory;
import lombok.Data;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import static com.jaspersoft.cli.tool.command.factory.CommandFactory.create;
import static java.util.Arrays.asList;

/**
 * This class is responsible for building a queue of commands with due
 * consideration of CLI tool rules:
 *      > Command
 *          > SubCommand
 *              > SubCommand of SubCommand
 *                  > and so on.
 *
 * @author A. Krasnyanskiy
 */
@Data
public class CommandBuilder {

    private Map<String, Command> commands = new TreeMap<>();

    /**
     * Builds chain of commands via {@link CommandFactory}
     * and populates the command holder map.
     *
     * @return the root command
     */
    public JCommander build() {

        commands = CommandFactory.create(asList("jrs", "import", "show", "info"));

        JCommander baseCmd = new JCommander();
        JCommander jrsCmd = addCommand(baseCmd, "jrs", commands.get("jrs"));
        JCommander importCmd = addCommand(jrsCmd, "import", commands.get("import"));
        JCommander listCmd = addCommand(jrsCmd, "show", commands.get("show"));
        JCommander infoCmd = addCommand(jrsCmd, "info", commands.get("info"));
        return baseCmd;
    }

    private JCommander addCommand(JCommander parentCommand, String commandName, Object parentSubCommand) {
        parentCommand.addCommand(commandName, parentSubCommand);
        return parentCommand.getCommands().get(commandName);
    }

    /**
     * Excludes unused commands from the map of commands.
     *
     * @param rootCmd the command from which we should start traversing
     * @return this
     */
    public CommandBuilder filter(JCommander rootCmd) {
        markCmd(rootCmd);
        Map<String, Command> filtered = new TreeMap<>();
        for (Entry<String, Command> entry : commands.entrySet()) {
            AbstractCommand cmd = ((AbstractCommand) entry.getValue());
            if (cmd.isActive()) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        commands = filtered;
        return this;
    }

    private void markCmd(JCommander rootCmd) {
        String cmdName = rootCmd.getParsedCommand();
        if (cmdName != null) {
            ((AbstractCommand) commands.get(cmdName)).setActive(true);
            if (rootCmd.getCommands().containsKey(cmdName)) {
                markCmd(rootCmd.getCommands().get(cmdName));
            }
        }
    }
}
