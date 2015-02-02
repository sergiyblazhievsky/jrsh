package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.ExecutionMode;
import com.jaspersoft.jasperserver.shell.exception.CannotCreateFileException;
import com.jaspersoft.jasperserver.shell.exception.CannotSaveProfileConfiguration;
import com.jaspersoft.jasperserver.shell.exception.MandatoryParameterMissingException;
import com.jaspersoft.jasperserver.shell.exception.UnknownInterfaceException;
import com.jaspersoft.jasperserver.shell.exception.WrongPathParameterException;
import com.jaspersoft.jasperserver.shell.exception.parser.ParameterValueSizeException;
import com.jaspersoft.jasperserver.shell.exception.parser.WrongRepositoryPathFormatException;
import com.jaspersoft.jasperserver.shell.exception.profile.CannotLoadProfileConfiguration;
import com.jaspersoft.jasperserver.shell.exception.profile.WrongProfileNameException;
import com.jaspersoft.jasperserver.shell.exception.server.GeneralServerException;
import com.jaspersoft.jasperserver.shell.exception.server.JrsResourceNotFoundException;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.Profile;
import com.jaspersoft.jasperserver.shell.profile.ProfileUtil;
import jline.console.ConsoleReader;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.ExecutionMode.SHELL;
import static com.jaspersoft.jasperserver.shell.profile.ProfileFactory.getInstance;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
@Data
@ToString(exclude = {"description", "parameters"})
public abstract class Command implements Executable, ConsoleReaderAware {

    protected String name;
    protected String description; // todo => rename to `briefDescription`
    protected String usageDescription; // ?
    protected List<Parameter> parameters = new ArrayList<>();
    protected static Profile profile = getInstance();

    private ExecutionMode mode;
    protected ConsoleReader reader;

    abstract void run();

    @Override
    public void execute() {
        try {
            run();
        } catch (Exception e) {
            if (mode.equals(SHELL)) {
                if (e instanceof WrongPathParameterException || e instanceof CannotCreateFileException || e instanceof CannotLoadProfileConfiguration || e instanceof CannotSaveProfileConfiguration) {
                    out.printf("i/o error: %s\n", e.getMessage());
                    return;
                }
                // we cannot handle thus cases, hence we print error and deny re-login operation below
                if (e instanceof WrongRepositoryPathFormatException ||
                        e instanceof JrsResourceNotFoundException ||
                        e instanceof ParameterValueSizeException ||
                        /* for replicate if [to] doesn't exist */
                        e instanceof WrongProfileNameException ||
                        e instanceof MandatoryParameterMissingException) {
                    out.printf("error: %s\n", e.getMessage());
                    return;
                }
                // re-login
                if (!ProfileUtil.isEmpty(profile) && /* for replicate only */!(e instanceof GeneralServerException)) {
                    String password = askPassword();
                    SessionFactory.create(profile.getUrl(), profile.getUsername(), password, profile.getOrganization());
                    run();
                } else {
                    throw new UnknownInterfaceException(e.getMessage());
                }
            } else {
                throw new UnknownInterfaceException(e.getMessage()); // we shouldn't even try to handle that kind of cases
            }
        }
    }

    private String askPassword() {
        String pass = null;
        try {
            pass = reader.readLine("\rPlease enter password: ");
        } catch (IOException ignored) {}
        reader.setPrompt("\u001B[1m>>> \u001B[0m");
        return pass;
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

    @Override
    public void setReader(ConsoleReader consoleReader) {
        this.reader = consoleReader;
    }
}
