package com.jaspersoft.cli.tool.core;

import com.jaspersoft.cli.tool.core.tree.DtoToTreeConverter;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.FOLDER_URI;
import static java.lang.System.exit;
import static java.lang.System.in;
import static java.lang.System.out;

/**
 * The core class for JRS CLI.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class CliEngine extends ClientRestServiceOperation implements Toolkit {

    private Options options = new Options();
    private String[] args;

    public CliEngine(String[] args) {
        this.args = args;
        options.addOption("url", true, "jrs url");
        options.addOption("u", "user", true, "username");
        options.addOption("p", "password", true, "password");
        options.addOption("f", "file", true, "zip file");
        options.addOption("pf", "print from specified folder", true, "print resources tree from the specified folder");
        options.addOption("pr", "print from the root", false, "print resources tree from the root");
    }

    @Override
    public void process() {
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getArgs().length == 0) {
                readCommand();
            } else {
                String[] commands = cmd.getArgs();
                Map<String, String> options = toMap(cmd.getOptions());
                switch (commands[0]) {
                    case "import": importData(options); break;
                    case "tree":   tree(options); break;
                    case "help":   help(); break;
                    case "exit":   exit(0x01);
                }
            }
        } catch (ParseException e) {
            help();
        }
    }

    @Override
    public void readCommand() {
        out.print("Please enter a command: ");
        Scanner scanner = new Scanner(in);
        if (scanner.hasNext()) {
            args = scanner.nextLine().split("\\s+");
            if (args.length > 0) {
                process();
            } else {
                try {
                    throw new ParseException("You haven't specified parameters of command.");
                } catch (ParseException e) {
                    help();
                }
            }
        }
    }

    @Override
    public void importData(Map<String, String> options) {
        session = connect(options.get("url"), options.get("p"), options.get("u"));
        if (session == null) {
            throw new RuntimeException("Session cannot be null.");
        } else {
            importResource(this.getClass().getClassLoader().getResourceAsStream(options.get("f")));
            cleanArgs();
        }
    }

    @Override
    public Session connect(String url, String usr, String pwd) {
        return new ClientConfigurationSessionFactory().configure(url, usr, pwd);
    }

    @Override
    public void tree(Map<String, String> options) {
        List<String> converted = new ArrayList<>();
        session = connect(options.get("url"), options.get("p"), options.get("u"));
        if (options.containsKey("pr")){
            List<ClientResourceLookup> lookup = session.resourcesService()
                    .resources().search()
                    .entity()
                    .getResourceLookups();
            for (ClientResourceLookup lkp : lookup) {
                converted.add(lkp.getUri());
            }
            new DtoToTreeConverter().tree(converted).print();
        } else {
            List<ClientResourceLookup> lookup = session.resourcesService()
                    .resources().parameter(FOLDER_URI, options.get("pf"))
                    .search().entity()
                    .getResourceLookups();
            for (ClientResourceLookup lkp : lookup) {
                converted.add(lkp.getUri());
            }
            new DtoToTreeConverter().tree(converted, options.get("pf")).print();
        }
        cleanArgs();
    }

    @Override
    public void help() {
        out.println("Usage: jrs <command> [<args>]\n" +
                    "\n" +
                    "   where <args> include:\n" +
                    "     -u\t  JRS username\n" +
                    "     -p\t  JRS password \n" +
                    "     -url JRS url \n" +
                    "     -f\t  path to zipped resource archive\n");
        cleanArgs();
    }

    @Override
    public void version() {}

    @Override
    public void profile() {}

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
