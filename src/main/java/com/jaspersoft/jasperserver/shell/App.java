package com.jaspersoft.jasperserver.shell;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.exception.InterfaceException;
import com.jaspersoft.jasperserver.shell.exception.parser.MandatoryParameterException;
import com.jaspersoft.jasperserver.shell.exception.server.ServerException;
import com.jaspersoft.jasperserver.shell.parser.CommandParser;
import com.jaspersoft.jasperserver.shell.validator.CommandParameterValidator;

import java.util.Queue;
import java.util.Scanner;
import java.util.logging.LogManager;

import static com.jaspersoft.jasperserver.shell.ExecutionMode.SHELL;
import static com.jaspersoft.jasperserver.shell.ExecutionMode.TOOL;
import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.create;
import static java.lang.System.exit;
import static java.lang.System.out;
import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
public class App {
    public static void main(String[] args) {

        App app = new App();
        Context context = new Context();
        Queue<Command> queue = null;
        CommandParser parser = new CommandParser(new CommandParameterValidator());
        parser.setContext(context);

        LogManager.getLogManager().reset(); // turn off Jersey logger

        if (args.length < 1) {
            out.println("Welcome to JRSH v0.1!\n");
            while (true) {
                String input = app.readLine();
                if ("".equals(input)) continue;
                try {
                    queue = parser.parse(input);
                    for (Command cmd : queue) {
                        if (cmd != null) cmd.setMode(SHELL);
                    }
                } catch (InterfaceException e) {
                    if (e instanceof MandatoryParameterException) {
                        Command cmd = create("help");
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
        } else /* we are in the tool mode */ {
            try {
                queue = parser.parse(args);
                for (Command cmd : queue) {
                    if (cmd != null) cmd.setMode(TOOL);
                }
                exit(0); // ok!
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

    private String readLine() {
        out.printf((char) 27 + "[33m>>> " + (char) 27 + "[37m");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}