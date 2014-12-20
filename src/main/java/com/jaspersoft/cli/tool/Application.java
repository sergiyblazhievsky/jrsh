package com.jaspersoft.cli.tool;

import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.common.CommandBuilder;
import com.jaspersoft.cli.tool.command.common.CommandProcessor;

import java.util.Map;

/**
 * The Tool class. It manages the components and interactions
 * between them.
 *
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
public class Application {
    public static void main(String[] args) {
        CommandProcessor processor = new CommandProcessor();
        CommandBuilder builder = new CommandBuilder(args);
        Map<String, AbstractCommand> commandSequence = builder.filter().getCommands();
        processor.process(commandSequence);
    }
}
