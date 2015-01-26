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
                String val = p.getValues().get(0);
                Command cmd = CommandFactory.create(val);
                out.printf("\t%s\t\t%s\n", cmd.getName(), cmd.getDescription());
            } catch (InterfaceException e) {
                out.printf("error: %s\n", e.getMessage());
            }
        } else {
            out.println("Available commands: ");

            // Java 8
            //context.getCmdDescription().entrySet().stream().filter(e -> e.getKey() != null)
            //        .forEach(e -> out.printf("\t%s\t\t%s\n", e.getKey(), e.getValue()));

            // Java 7
            for (Entry<String, String> e : context.getCmdDescription().entrySet()) {
                if (e.getKey()!= null){
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