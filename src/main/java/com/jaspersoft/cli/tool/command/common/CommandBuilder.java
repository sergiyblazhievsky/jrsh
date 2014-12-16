package com.jaspersoft.cli.tool.command.common;

import com.beust.jcommander.JCommander;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.Command;
import lombok.Data;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import static com.jaspersoft.cli.tool.command.factory.CommandFactory.create;
import static java.util.Arrays.asList;

/**
 * This class is responsible for building a queue of commands with due
 * consideration of CLI tool rules:
 * Command -> SubCommand -> SubCommand of SubCommand and so on.
 *
 * @author A. Krasnyanskiy
 */
@Data
public class CommandBuilder {

    private Map<String, Command> commands = new TreeMap<>(); // fixme: remove the comparator is doesn't work

    /**
     * Builds commands via factory {@link com.jaspersoft.cli.tool.command.factory.CommandFactory}
     * and populates the command holder map.
     *
     * @return the root command
     */
    public JCommander build() {

        // todo: add or replace with xml context configuration | this is a high priority task
        commands = create(/*new HashSet<>(*/asList("jrs", "import", "list", "info", "apply", "revert", "child", "export")/*)*/);

        //
        // builds command sequence (cmd queue | CLI rules) considering the level of command
        //
        JCommander baseCmd = new JCommander();
        JCommander jrsCmd = addCommand(baseCmd, "jrs", 1, commands.get("jrs"));
        JCommander importCmd = addCommand(jrsCmd, "import", 2, commands.get("import"));
        JCommander listCmd = addCommand(jrsCmd, "list", 2, commands.get("list"));
        JCommander infoCmd = addCommand(jrsCmd, "info", 2, commands.get("info"));
        JCommander applyCmd = addCommand(listCmd, "apply", 3, commands.get("apply"));
        JCommander revertCmd = addCommand(listCmd, "revert", 3, commands.get("revert"));
        JCommander exportCmd = addCommand(infoCmd, "export", 3, commands.get("export"));
        JCommander childCmd = addCommand(applyCmd, "child", 4, commands.get("child"));


        return baseCmd;
    }

    private JCommander addCommand(JCommander parentCommand, String commandName, int order, Object parentSubCommand) {
        ((AbstractCommand) parentSubCommand).setOrder(order);
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
