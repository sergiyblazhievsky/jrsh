package com.jaspersoft.cli.tool;

import com.beust.jcommander.JCommander;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.common.ArgumentAppender;
import com.jaspersoft.cli.tool.command.common.CommandBuilder;
import com.jaspersoft.cli.tool.command.common.CommandProcessor;

import java.util.Map;

/**
 * Main class of whole app. It manages the creation of app components
 * and interaction between them.
 *
 * @author A. Krasnyanskiy
 * @since 1.0
 */
public class Application {
    public static void main(String[] args) {

        CommandBuilder builder = new CommandBuilder();
        JCommander rootCommand = builder.build();
        rootCommand.parse(ArgumentAppender.append(args));


        Map<String, AbstractCommand> executableCommandQueue =
                builder.filter(rootCommand).getCommands();


        CommandProcessor processor = new CommandProcessor();
        processor.processInOrder(executableCommandQueue);
    }
}
