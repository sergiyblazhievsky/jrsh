package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.context.ContextAware;
import com.jaspersoft.jasperserver.shell.exception.InterfaceException;
import com.jaspersoft.jasperserver.shell.factory.CommandFactory;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;

import java.util.Map.Entry;

import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class HelpCommand extends Command implements ContextAware {

    private Context context;

    /*



     */


    public HelpCommand() {
        name = "help";
        description = "Show all information about JRSH.";
        parameters.add(new Parameter().setName("anonymous").setOptional(true));
    }

    @Override
    void run() {
        Parameter p = parameter("anonymous");
        if (p != null && !p.getValues().isEmpty()) {
            try {

                // help <subcommand>

                String val = p.getValues().get(0);
                Command cmd = CommandFactory.create(val);
                out.printf("\t%s\t\t%s\n", cmd.getName(), cmd.getDescription());

                String fullDesc = cmd.getComprehensiveDescription();
                if (fullDesc != null) {
                    out.println(cmd.getComprehensiveDescription());
                }
            } catch (InterfaceException e) {
                out.printf("error: %s\n", e.getMessage());
            }
        } else {

            // help

            out.println("Usage: jrsh <subcommand> [options] [args]");
            out.println("JasperReports Server command-line client, version 0.1");
            out.println("Type 'jrsh help <subcommand>' for help on a specific subcommand.");

            out.println("\n\u001B[30;44mAvailable commands: \u001B[0m");
            for (Entry<String, String> e : context.getCmdDescription().entrySet()) {
                if (e.getKey() != null) {
                    out.printf("\t%s\t\t%s\n", e.getKey(), e.getValue());
                }
            }
        }
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}