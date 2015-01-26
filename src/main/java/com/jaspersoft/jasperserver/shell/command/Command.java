package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.ExecutionMode;
import com.jaspersoft.jasperserver.shell.exception.UnknownInterfaceException;
import com.jaspersoft.jasperserver.shell.exception.WrongPathParameterException;
import com.jaspersoft.jasperserver.shell.exception.parser.WrongRepositoryPathFormatException;
import com.jaspersoft.jasperserver.shell.exception.server.JrsResourceNotFoundException;
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

/**
 * @author Alexander Krasnyanskiy
 */
@Data
@ToString(exclude = {"description", "parameters"})
public abstract class Command implements Executable {

    protected String name;
    protected String description; // todo => rename to `brief description`
    protected String comprehensiveDescription; // ?
    protected List<Parameter> parameters = new ArrayList<>();
    protected static Profile profile = getInstance();

    private ExecutionMode mode;

    abstract void run();

    @Override
    public void execute() {
        try {
            run();
        } catch (Exception e) {
            if (mode.equals(SHELL)) {
                if (e instanceof WrongPathParameterException) {
                    out.printf("i/o error: %s\n", e.getMessage());
                    return;
                }

                // we cannot handle thus cases, hence we print error and deny re-login operation below
                if (e instanceof WrongRepositoryPathFormatException || e instanceof JrsResourceNotFoundException){
                    out.printf("error: %s\n", e.getMessage());
                    return;
                }

                // re-login
                if (!profile.isEmpty()) {
                    String password = askPassword();
                    SessionFactory.create(profile.getUrl(), profile.getUsername(), password, profile.getOrganization());
                    run();
                } else {
                    throw new UnknownInterfaceException(e.getMessage());
                }
            } else {
                throw new UnknownInterfaceException(e.getMessage()); // We shouldn't even try to handle that kind of cases
            }
        }
    }

    private String askPassword() {
        out.print("Please enter password: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public Parameter parameter(String key) {
        for (Parameter p : parameters) {
            if (key.equals(p.getName())) return p;
            if (p.getKey() != null && p.getKey().equals(key)) return p;
        }
        for (Parameter p : parameters) {
            if ("anonymous".equals(p.getName())) return p;
        }
        return null;
    }

    public Parameter nonAnonymousParameter(String key) {
        for (Parameter p : parameters) {
            if (key.equals(p.getName())) return p;
            if (p.getKey() != null && p.getKey().equals(key)) return p;
        }
        return null;
    }
}
