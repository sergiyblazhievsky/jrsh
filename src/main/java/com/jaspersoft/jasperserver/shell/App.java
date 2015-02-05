package com.jaspersoft.jasperserver.shell;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.completion.ParameterCompleter;
import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.encoding.FileEncodingUtil;
import com.jaspersoft.jasperserver.shell.exception.InterfaceException;
import com.jaspersoft.jasperserver.shell.exception.parser.MandatoryParameterException;
import com.jaspersoft.jasperserver.shell.exception.server.ServerException;
import com.jaspersoft.jasperserver.shell.parser.CommandParser;
import com.jaspersoft.jasperserver.shell.profile.Profile;
import com.jaspersoft.jasperserver.shell.profile.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.ProfileConfigurationFactory;
import com.jaspersoft.jasperserver.shell.validator.CommandParameterValidator;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.StringsCompleter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private final static String FILE = "profiles.yml";

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

            //
            // Completers configuration
            //
            StringsCompleter exit = new StringsCompleter("exit");

            StringsCompleter session = new StringsCompleter("session");
            StringsCompleter logout = new StringsCompleter("logout");

            StringsCompleter profile = new StringsCompleter("profile");
            ParameterCompleter profileParams = new ParameterCompleter(asList("save", "load", "list"));

            StringsCompleter login = new StringsCompleter("login");
            ParameterCompleter loginParams = new ParameterCompleter(asList("--server", "--username", "--password"));

            StringsCompleter show = new StringsCompleter("show");
            ParameterCompleter showParams = new ParameterCompleter(asList("repo", "server-info"));

            StringsCompleter replicate = new StringsCompleter("replicate");


            ///
            // Profiles pre-loading
            //
            File file = new File(System.getProperty("user.dir"));
            List<String> list = new ArrayList<>();
            list.add("to");
            try {
                List<Profile> profs = preLoadProfiles(file.getParentFile() + "/config/" + FILE);
                for (Profile p : profs) {
                    String name = p.getName();
                    if (name != null && !name.isEmpty()){
                        list.add(name);
                    }
                }
            } catch (Exception ignored){}

            ParameterCompleter replicateParams = new ParameterCompleter(list);

            StringsCompleter export = new StringsCompleter("export");
            ParameterCompleter exportParams = new ParameterCompleter(asList("to", "without-access-events",
                    "with-audit-events", "with-monitoring-events", "with-events", "with-users-and-roles",
                    "with-repository-permissions"));

            StringsCompleter importCmd = new StringsCompleter("import");
            ParameterCompleter importParams = new ParameterCompleter(asList("with-audit-events", "with-access-events",
                    "with-monitoring-events", "with-events", "with-update", "with-skip-user-update"));

            StringsCompleter help = new StringsCompleter("help");
            ParameterCompleter helpParams = new ParameterCompleter(asList("login", "logout", "import", "export", "exit",
                    "show", "session", "replicate", "profile"));


            ArgumentCompleter loginCompleter = new ArgumentCompleter(login, loginParams);
            ArgumentCompleter exportCompleter = new ArgumentCompleter(export, exportParams);
            ArgumentCompleter helpCompleter = new ArgumentCompleter(help, helpParams);
            ArgumentCompleter showCompleter = new ArgumentCompleter(show, showParams);
            ArgumentCompleter importCompleter = new ArgumentCompleter(importCmd, importParams);
            ArgumentCompleter profileCompleter = new ArgumentCompleter(profile, profileParams);
            ArgumentCompleter replicateCompleter = new ArgumentCompleter(replicate, replicateParams);

            AggregateCompleter general = new AggregateCompleter(exit, logout, /*replicate*/replicateCompleter,
                    profileCompleter, session, loginCompleter, importCompleter, showCompleter, exportCompleter,
                    helpCompleter);

            console.addCompleter(general);

            String input;
            while ((input = console.readLine().trim()) != null) {

                if ("".equals(input)) continue;
                if ("clear".equals(input)) console.clearScreen(); // secret command :)

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
                    if (cmd != null) {
                        cmd.setMode(TOOL);
                        cmd.execute();
                    }
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

    private static List<Profile> preLoadProfiles(String path) throws FileNotFoundException {
        ProfileConfiguration config = ProfileConfigurationFactory.create(path);
        return config.getProfiles();
    }
}