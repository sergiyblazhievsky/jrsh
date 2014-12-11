package com.jaspersoft.cli.tool.core;

import com.jaspersoft.cli.tool.core.tree.DtoToTreeConverter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.in;
import static java.lang.System.out;

public class Engine extends ServiceOperation {


    private Options options = new Options();
    private String[] args;


    public Engine() {
        options.addOption("url", true, "jrs url");
        options.addOption("u", "user", true, "username");
        options.addOption("p", "password", true, "password");
        options.addOption("f", "file", true, "zip file");
        options.addOption("pf", "print from specified folder", true, "print resources tree from the specified folder");
        options.addOption("pr", "print from the root", false, "print resources tree from the root");
    }


    public void run(String[] args) {
        this.args = args;
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getArgs().length == 0) {
                readCommand();
            } else {
                String[] commands = cmd.getArgs();
                Map<String, String> options = toMap(cmd.getOptions());
                switch (commands[0]) {
                    case "import":
                        importData(options);
                        break;
                    case "tree":
                        tree(options);
                        break;
                    case "help":
                        help();
                        break;
                    case "exit":
                        exit(0x01);
                }
            }
        } catch (ParseException e) {
            out.println("Wrong command or option. See help info below:");
            help();
        }
    }

    public void readCommand() {
        out.print("Please enter a command: ");
        Scanner scanner = new Scanner(in);
        if (scanner.hasNext()) {
            args = scanner.nextLine().split("\\s+");
            if (args.length > 0) {
                run(args);
            } else {
                help();
            }
        }
    }

    public void importData(Map<String, String> options) {
        session = connect(options.get("url"), options.get("p"), options.get("u"));
        if (session == null) {
            throw new RuntimeException("Session cannot be null.");
        } else {
            String path = options.get("f");
            File zip = new File(path);
            importResource(zip);
            cleanArgs();
        }
    }

    public Session connect(String url, String usr, String pwd) {
        return new ClientConfigurationSessionFactory().configure(url, usr, pwd);
    }

    public void tree(Map<String, String> options) {

        session = connect(options.get("url"), options.get("p"), options.get("u"));
        DtoToTreeConverter converter = new DtoToTreeConverter();

        if (options.containsKey("pr")) {
            converter.toTree(resourceAsList()).print();
        } else if (options.get("pf") != null) {
            String fromFolder = options.get("pf");
            converter.toTree(resourceAsList(options), fromFolder).print();
        }
        cleanArgs();
    }

    public void help() {
        out.println("Usage: jrs <command> [<args>]\n" +
                "\n" +
                "   where <args> include:\n" +
                "     -u\t  JRS username\n" +
                "     -p\t  JRS password \n" +
                "     -url JRS url \n" +
                "     -f\t  path to zipped resource archive\n" +
                "     -pf\t print from specified folder option\n" +
                "     -pr\t print from the root option");

        cleanArgs();
    }

    private void cleanArgs() {
        args = null;
    }

    private Map<String, String> toMap(Option[] options) {
        HashMap<String, String> opts = new HashMap<>();
        for (Option option : options) {
            opts.put(option.getOpt(), option.getValue());
        }
        return opts;
    }
}
