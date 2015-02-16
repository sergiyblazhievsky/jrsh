package com.jaspersoft.jasperserver.shell.context;

import com.jaspersoft.jasperserver.shell.command.Command;
import lombok.Data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.createCommand;
import static com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory.createConfiguration;
import static java.util.Arrays.asList;

@Data
@Deprecated
public class Context {

    private Map<String, String> cmdDescription = new HashMap<>();
    private List<String> dictionary
            = new ArrayList<>(asList("help", "?", "import", "replicate", "export",
            "profile", "session", "logout", "login", "exit", "show", "clear"));

    public Context() {
        try {
            createConfiguration("/usr/conf/profiles.yml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String v : dictionary) {
            Command c = createCommand(v);
            cmdDescription.put(c.getName(), c.getDescription());
        }
    }
}
