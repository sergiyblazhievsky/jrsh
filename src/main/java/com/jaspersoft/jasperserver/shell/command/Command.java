package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.ExecutionMode;
import com.jaspersoft.jasperserver.shell.exception.UnknownInterfaceException;
import com.jaspersoft.jasperserver.shell.exception.WrongPathParameterException;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.Profile;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.jaspersoft.jasperserver.shell.ExecutionMode.SHELL;
import static com.jaspersoft.jasperserver.shell.profile.ProfileFactory.getInstance;
import static java.lang.System.out;
import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
@Data
@ToString(exclude = {"description", "parameters"})
public abstract class Command implements Executable {

    protected String name;
    protected String description;
    protected List<Parameter> parameters = new ArrayList<>();
    protected static Profile profile = getInstance(); // default

    private ExecutionMode mode;

    // 1
    abstract void run();

    // 2
    @Override
    public void execute() { // template method
        try {
            run(); // main run of command
        } catch (Exception e) { // todo => specify exception type :: ?
            // check


            //      LOG     out.printf("log => %s %s\n", e.getMessage(), e.getClass());



            if (mode.equals(SHELL)) {



                if (e instanceof WrongPathParameterException) { // => it shouldn't be an i/o exception
                    out.printf("i/o error: %s\n", e.getMessage());
                    return;
                }



                if (!profile.isEmpty()) {
                    // ask password
                    String password = askPassword();
                    // use session to login
                    SessionFactory.create(profile.getUrl(), profile.getUsername(), password, profile.getOrganization());



                    // ok, you have updated a session. that's cool. but what's next?
                    // maybe we may try to execute the command again?



                    run();



                } else {
                    throw new UnknownInterfaceException(e.getMessage()); // todo: <A1> это удалить, строку ниже раскомментировать
                    //out.printf(" + error: %s\n", e.getMessage());
                }
            } else {
                throw new UnknownInterfaceException(e.getMessage());
            }
        }
    }

    // 2.1
    private String askPassword() {
        out.print("Please enter password: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    // 3
    public Parameter parameter(String key) { // paramName | paramKey
        for (Parameter p : parameters) {
            if (key.equals(p.getName())) {
                return p;
            }
            if (p.getKey() != null && p.getKey().equals(key)) {
                return p;
            }
        }
        for (Parameter p : parameters) {
            if ("anonymous".equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    // 4
    boolean areParametersSet(String... exclusions) {
        for (Parameter p : parameters) {
            if (!asList(exclusions).contains(p.getName()) && p.getValues().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // 5
    public Parameter nonAnonymousParameter(String key) {
        for (Parameter p : parameters) {
            if (key.equals(p.getName())) {
                return p;
            }
            if (p.getKey() != null && p.getKey().equals(key)) {
                return p;
            }
        }
        return null;
    }
}
