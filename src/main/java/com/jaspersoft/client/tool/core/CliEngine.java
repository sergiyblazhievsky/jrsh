package com.jaspersoft.client.tool.core;

import com.jaspersoft.client.tool.core.api.toolkit.Toolkit;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class CliEngine implements Toolkit {


    private static final Logger log = Logger.getLogger(CliEngine.class.getName());
    private Options options = new Options();
    private String[] args;


    public CliEngine(String[] args) {
        this.args = args;

        // short names
        options.addOption("h", "help", false, "show help.");
        options.addOption("v", "var", true, "Here you can set parameter .");
        options.addOption("i", "import", true, "import resources");


        // long names
        options.addOption("server", true, "server parameters");
    }


    @Override
    public void handle() {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                help();
            }
            if (cmd.hasOption("v")) {
                log.log(INFO, "Using cli argument -v=" + cmd.getOptionValue("v"));
            }
            if (cmd.hasOption("i")){
                String val = cmd.getOptionValue("i");
                System.out.println(val);
            } if (cmd.hasOption("server")) {
                String val = cmd.getOptionValue("server");
                System.out.println(val);
            } else {

                // todo: default _

                log.log(SEVERE, "Missing v option");
                help();
            }
        } catch (ParseException e) {
            log.log(SEVERE, "Failed to handle comand line properties", e);
            help();
        }
    }


    @Override
    public void help() {

    }

    @Override
    public void importData() {

    }

    @Override
    public void profile() {

    }

    @Override
    public void server() {

    }

    @Override
    public void version() {

    }
}
