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
        CommandParser parser = new CommandParser(new CommandParameterValidator());
        parser.setContext(context);

        LogManager.getLogManager().reset(); // shut down Jersey logger

        if (args.length < 1) {

            out.println("Welcome to JRSH v1.0!\n");

            while (true) {
                String input = app.readLine();


                if ("".equals(input)) continue;


                Queue<Command> queue;

                try {
                    queue = parser.parse(input);


                    queue.stream().filter(c -> c != null).forEach(c -> c.setMode(SHELL)); // fixme => think about refactoring


                } catch (InterfaceException e) {
                    if (e instanceof MandatoryParameterException) {


                        // => if there is no mandatory parameters then show help for that command
                        Command cmd = create("help");
                        cmd.parameter("anonymous").setValues(asList(/* command = */e.getMessage()));
                        cmd.execute();
                    } else {
                        out.printf("error: %s\n", e.getMessage());
                    }

                    continue;
                }

                try {
                    // execute the commands sequence
                    queue.stream().filter(c -> c != null).forEach(Command::execute); //queue.forEach(Command::execute);
                } catch (ServerException e) {
                    out.printf("error: %s\n", e.getMessage());
                }


                // => catch
                // todo: убрать этот catch вместе с <A1>

                catch (InterfaceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            // Tool Mode
            // todo => ?
            Queue<Command> queue = null;
            try {
                queue = parser.parse(args);
                queue.stream().filter(c -> c != null).forEach(c -> c.setMode(TOOL));
            } catch (InterfaceException e) {
                out.printf(e.getMessage());
                exit(1);
            }
            try {
                queue.stream().filter(c -> c != null).forEach(Command::execute);
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
