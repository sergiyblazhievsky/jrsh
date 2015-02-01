package com.jaspersoft.jasperserver.shell;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.ReplicateCommand;
import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.encoding.FileEncodingUtil;
import com.jaspersoft.jasperserver.shell.exception.InterfaceException;
import com.jaspersoft.jasperserver.shell.exception.parser.MandatoryParameterException;
import com.jaspersoft.jasperserver.shell.exception.server.ServerException;
import com.jaspersoft.jasperserver.shell.parser.CommandParser;
import com.jaspersoft.jasperserver.shell.validator.CommandParameterValidator;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.StringsCompleter;

import java.io.IOException;
import java.util.Queue;
import java.util.logging.LogManager;

import static com.jaspersoft.jasperserver.shell.ExecutionMode.TOOL;
import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.create;
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
        CommandParser parser = new CommandParser(new CommandParameterValidator());
        parser.setContext(context);

        LogManager.getLogManager().reset(); // turn off Jersey logger
        FileEncodingUtil.setUTF8(); // issue #53 fix candidate

        if (args.length < 1) {

            console = new ConsoleReader();
            out.println("Welcome to JRSH v1.0!\n");
            console.setPrompt("\u001B[1m>>> \u001B[0m");

            /**
             ** Completers configuration
             **/

            StringsCompleter exit = new StringsCompleter("exit");
            StringsCompleter replicate = new StringsCompleter("replicate");
            StringsCompleter session = new StringsCompleter("session");
            StringsCompleter logout = new StringsCompleter("logout");

            StringsCompleter profile = new StringsCompleter("profile");
            StringsCompleter profileParams = new StringsCompleter("save", "load", "list");

            StringsCompleter login = new StringsCompleter("login");
            StringsCompleter loginParams = new StringsCompleter("--server", "--username", "--password",
                    "--organization");

            StringsCompleter show = new StringsCompleter("show");
            StringsCompleter showParams = new StringsCompleter("repo", "server-info");

            StringsCompleter export = new StringsCompleter("export");
            StringsCompleter exportParams = new StringsCompleter("to", "without-access-events", "with-audit-events",
                    "with-monitoring-events", "with-events", "with-users-and-roles", "with-repository-permissions");

            StringsCompleter importCmd = new StringsCompleter("import");
            StringsCompleter importParams = new StringsCompleter("with-audit-events", "with-access-events",
                    "with-monitoring-events", "with-events", "with-update", "with-skip-user-update");

            StringsCompleter help = new StringsCompleter("help");
            StringsCompleter helpParams = new StringsCompleter("login", "logout", "import", "export", "exit",
                    "show", "session", "replicate", "profile");

            ArgumentCompleter loginCompleter = new ArgumentCompleter(login, loginParams);
            ArgumentCompleter exportCompleter = new ArgumentCompleter(export, exportParams);
            ArgumentCompleter helpCompleter = new ArgumentCompleter(help, helpParams);
            ArgumentCompleter showCompleter = new ArgumentCompleter(show, showParams);
            ArgumentCompleter importCompleter = new ArgumentCompleter(importCmd, importParams);
            ArgumentCompleter profileCompleter = new ArgumentCompleter(profile, profileParams);

            AggregateCompleter general = new AggregateCompleter(exit, logout, replicate, profileCompleter, session,
                    loginCompleter, importCompleter, showCompleter, exportCompleter, helpCompleter);

            console.addCompleter(general);

            String input;
            while ((input = console.readLine().trim()) != null) {

                if ("".equals(input)) continue;

                try {
                    queue = parser.parse(input);
                    for (Command cmd : queue) {
                        if (cmd != null) {
                            cmd.setMode(ExecutionMode.SHELL);
                            if (cmd instanceof ReplicateCommand){
                                ((ReplicateCommand)cmd).setReader(console);
                            }
                        }
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

//    private String readLine() {
//        out.printf("\u001B[1m>>> \u001B[0m");
//        Scanner in = new Scanner(System.in);
//        return in.nextLine();
//    }
}