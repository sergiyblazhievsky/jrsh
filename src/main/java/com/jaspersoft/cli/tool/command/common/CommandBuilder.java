package com.jaspersoft.cli.tool.command.common;

import com.beust.jcommander.JCommander;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.factory.CommandFactory;
import com.jaspersoft.cli.tool.command.impl.ShowCommand;
import com.jaspersoft.cli.tool.command.impl.ShowRepoCommand;
import com.jaspersoft.cli.tool.command.impl.ShowServerInfoCommand;
import lombok.Data;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * This class is responsible for building a queue of commands with due
 * consideration of CLI tool rules:
 * command => subcommand => subcommand-of-subcommand => and so on.
 *
 * @author A. Krasnyanskiy
 */
@Data
public class CommandBuilder {

    private Map<String, AbstractCommand> commands = new TreeMap<>();
    private JCommander baseCmd = new JCommander();

    /**
     * Build chain of commands.
     *
     * @param args app arguments
     */
    public CommandBuilder(String... args) {

        // Available commands in the app
        commands = CommandFactory.create("jrs", "help", "import", "show", "server-info", "repo");

        // Set chain of commands (sequence | tree)
        JCommander jrsCmd = addCommand(baseCmd, commands.get("jrs"));
        addCommand(jrsCmd, commands.get("help"));
        addCommand(jrsCmd, commands.get("import"));
        JCommander showCmd = addCommand(jrsCmd, commands.get("show"));
        addCommand(showCmd, commands.get("repo"));
        addCommand(showCmd, commands.get("server-info"));

        baseCmd.parse(DefaultArgumentAppender.append(args));
    }

    // Command binder
    private JCommander addCommand(JCommander parentCommand, AbstractCommand parentSubCommand) {
        parentCommand.addCommand(parentSubCommand.getCommandName(), parentSubCommand);
        return parentCommand.getCommands().get(parentSubCommand.getCommandName());
    }

    /**
     * Excludes unused commands from the map of commands. We need this method
     * for the reason that JCommander invert the order of commands. Instead of
     * [first > second > third] it returns [third > second > first].
     *
     * @return this
     */
    public CommandBuilder filter() {
        markCmd(baseCmd);
        Map<String, AbstractCommand> filtered = new TreeMap<>();
        for (Entry<String, AbstractCommand> entry : commands.entrySet()) {
            AbstractCommand cmd = entry.getValue();
            if (cmd.isActive()) {
                filtered.put(entry.getKey(), entry.getValue());
                establishPaternity(cmd);
            }
        }
        commands = filtered;
        return this;
    }

    /**
     * Simple command marker which is used for sorting.
     *
     * @param rootCmd unsorted command chain
     */
    private void markCmd(JCommander rootCmd) {
        String cmdName = rootCmd.getParsedCommand();
        if (cmdName != null) {
            commands.get(cmdName).setActive(true);
            if (rootCmd.getCommands().containsKey(cmdName)) {
                markCmd(rootCmd.getCommands().get(cmdName));
            }
        }
    }

    /**
     * Set a flag for transit command (i.e. show). We need that flag
     * to handle (throw proper exception) the case when required
     * subcommand hasn't specified.
     *
     * @param cmd current command
     */
    private void establishPaternity(AbstractCommand cmd) {
        if (cmd instanceof ShowRepoCommand) {
            ShowCommand.establishPaternity();
        }
        if (cmd instanceof ShowServerInfoCommand) {
            ShowCommand.establishPaternity();
        }
    }
}
