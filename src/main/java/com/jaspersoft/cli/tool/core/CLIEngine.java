package com.jaspersoft.cli.tool.core;

import com.jaspersoft.cli.tool.api.toolkit.Toolkit;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Scanner;
import java.util.logging.Logger;

import static java.lang.System.exit;
import static java.lang.System.in;
import static java.lang.System.out;
import static java.util.logging.Level.SEVERE;

/**
 * The core class for JRS CLI.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class CliEngine extends ClientSimpleOperation implements Toolkit {


    private static final Logger log = Logger.getLogger(CliEngine.class.getName());
    private Options options = new Options();
    private String[] args;


    public CliEngine(String[] args) {
        this.args = args;
        options.addOption("h", "help", false, "show help.");
        options.addOption("url", "server-url", true, "JasperReport Server URL");
        options.addOption("u", "user", true, "username");
        options.addOption("p", "password", true, "password");
        options.addOption("f", "path-to-file", true, "path to resource file");
    }


    @Override
    public void run() {
        CommandLineParser parser = new BasicParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getArgs().length == 0) {
                help();
                reedStdin();
            } else {
                String singleCommand = cmd.getArgs()[0];
                Option[] opts = cmd.getOptions();
                switch (singleCommand) {
                    case "server":
                        server(opts);
                        break;
                    case "import":
                        importData(opts);
                        break;
                    case "exit":
                        exit(0x01);
                }
            }
        } catch (ParseException e) {
            help();
            log(e);
        }
    }


    @Override
    public void reedStdin() {
        out.print("Please enter next command: ");
        Scanner scanner = new Scanner(in);
        if (scanner.hasNext()) {
            args = scanner.nextLine().split("\\s+");
            if (args.length > 0) {
                run();
            } else {
                try {
                    throw new ParseException("You haven't specified the command parameters.");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void log(Exception e) {
        log.log(SEVERE, "Error", e);
    }

    @Override
    public void importData(Option[] options) {
        if (session == null) {
            throw new RuntimeException("Session cannot be null.");
        } else {
            importResource(this.getClass().getClassLoader().getResourceAsStream(options[0].getValue()));
            cleanArgs();
            run();
        }
    }

    @Override
    public void server(Option[] options) {

        String url = null,
               pass = null,
               name = null;

        for (Option option : options) {
            String opt = option.getOpt();
            String val = option.getValue();
            if (opt.equals("url")) {
                url = val;
                continue;
            }
            if (opt.equals("p")) {
                pass = val;
                continue;
            }
            if (opt.equals("u")) name = val;
        }
        session = new ClientConfigurationSessionFactory().configure(url, name, pass);
        cleanArgs();
        run();
    }

    @Override
    public void version() {
        // todo: get JRS version
    }

    @Override
    public void profile() {
        // todo: add profile
    }

    @Override
    public void help() {
        // todo: show some useful info
    }

    private void cleanArgs() {
        args = null;
    }
}
