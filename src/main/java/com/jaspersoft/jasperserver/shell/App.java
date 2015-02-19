package com.jaspersoft.jasperserver.shell;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.completion.CompletionConfigurer;
import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.exception.InterfaceException;
import com.jaspersoft.jasperserver.shell.exception.parser.MandatoryParameterException;
import com.jaspersoft.jasperserver.shell.exception.server.ServerException;
import com.jaspersoft.jasperserver.shell.parser.CommandParser;
import com.jaspersoft.jasperserver.shell.validator.ParameterValidator;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;

import java.io.IOException;
import java.util.Queue;
import java.util.logging.LogManager;

import static com.jaspersoft.jasperserver.shell.ExecutionMode.TOOL;
import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.createCommand;
import static java.lang.System.exit;
import static java.lang.System.out;
import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
public class App {

    public static void main(String[] args) throws IOException {
        Context context = new Context();
        Queue<Command> queue = null;
        ConsoleReader console;
        CommandParser parser = new CommandParser(new ParameterValidator());
        parser.setContext(context);
        LogManager.getLogManager().reset();

        if (args.length < 1) {
            console = new ConsoleReader();
            out.println("Welcome to JRSH v1.0-alpha!\n");
            console.setPrompt("\u001B[1m>>> \u001B[0m");
            AggregateCompleter aggregator = new CompletionConfigurer().getAggregator();
            console.addCompleter(aggregator);
            String input;

            while ((input = console.readLine().trim()) != null) {
                if ("".equals(input)) continue;
                try {
                    queue = parser.parse(input);
                    for (Command cmd : queue) {
                        if (cmd != null) {
                            cmd.setMode(ExecutionMode.SHELL);
                            cmd.setReader(console);
                        }
                    }
                } catch (InterfaceException e) {
                    if (e instanceof MandatoryParameterException) {
                        Command cmd = createCommand("help");
                        cmd.parameter("anonymous").setValues(asList(e.getMessage()));
                        cmd.execute();
                    } else {
                        out.printf("error: %s\n", e.getMessage());
                    }
                    continue;
                }
                try {
                    for (Command c : queue) {
                        if (c != null) c.execute();
                    }
                } catch (ServerException e) {
                    out.printf("error: %s\n", e.getMessage());
                } catch (InterfaceException e) {
                    out.println(e.getMessage());
                }
            }
        } else {
            try {
                queue = parser.parse(args);
                for (Command cmd : queue) {
                    if (cmd != null) {
                        cmd.setMode(TOOL);
                        cmd.execute();
                    }
                }
                exit(0);
            } catch (InterfaceException e) {
                out.printf(e.getMessage());
                exit(1);
            }
            try {
                for (Command cmd : queue) {
                    if (cmd != null) cmd.execute();
                }
            } catch (ServerException | InterfaceException e) {
                out.printf(e.getMessage());
                exit(1);
            }
        }
    }
}