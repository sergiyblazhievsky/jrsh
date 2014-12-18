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
 * > Command
 * > SubCommand
 * > SubCommand of SubCommand
 * > and so on.
 *
 * @author A. Krasnyanskiy
 */
@Data
public class CommandBuilder {

    private Map<String, AbstractCommand> commands = new TreeMap<>();

    /**
     * Builds chain of commands via {@link CommandFactory}
     * and populates the command holder map.
     *
     * @return the root command
     */
    public JCommander build() {

        //
        // configure all commands storage
        //
        commands = CommandFactory.create("jrs", "import", "show", "info", "server-info", "repo");

        //
        // build chain of commands (sequence)
        //
        JCommander baseCmd = new JCommander();
        JCommander jrsCmd = addCommand(baseCmd, commands.get("jrs"));
        JCommander importCmd = addCommand(jrsCmd, commands.get("import"));
        JCommander showCmd = addCommand(jrsCmd, commands.get("show"));
        JCommander infoCmd = addCommand(jrsCmd, commands.get("info"));
        JCommander resourcesCmd = addCommand(showCmd, commands.get("repo"));
        JCommander serverInfoCmd = addCommand(showCmd, commands.get("server-info"));

        return baseCmd;
    }

    private JCommander addCommand(JCommander parentCommand, AbstractCommand parentSubCommand) {
        parentCommand.addCommand(parentSubCommand.getCommandName(), parentSubCommand);
        return parentCommand.getCommands().get(parentSubCommand.getCommandName());
    }

    /**
     * Excludes unused commands from the map of commands.
     *
     * @param rootCmd the command from which we should start traversing
     * @return this
     */
    public CommandBuilder filter(JCommander rootCmd) {
        markCmd(rootCmd);
        Map<String, AbstractCommand> filtered = new TreeMap<>();
        for (Entry<String, AbstractCommand> entry : commands.entrySet()) {
            AbstractCommand cmd = entry.getValue();
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
            commands.get(cmdName).setActive(true);
            if (rootCmd.getCommands().containsKey(cmdName)) {
                markCmd(rootCmd.getCommands().get(cmdName));
            }
        }
    }
}
