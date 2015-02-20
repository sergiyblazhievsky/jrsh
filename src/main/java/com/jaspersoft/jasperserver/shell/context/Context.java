package com.jaspersoft.jasperserver.shell.context;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.factory.ProfileFactory;
import com.jaspersoft.jasperserver.shell.profile.util.ProfileUtil;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.createCommand;
import static com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory.createConfiguration;
import static java.util.Arrays.asList;

@Data
@Deprecated
public class Context {

    private Map<String, String> description = new HashMap<>();
    private List<String> dictionary
            = new ArrayList<>(asList("help", "?", "import", "replicate", "export",
            "profile", "session", "logout", "login", "exit", "show", "clear"));

    public Context() {
        try {

            Properties properties = new Properties();
            InputStream stream = Context.class.getClass().getResourceAsStream("/context.properties");
            properties.load(stream);
            ProfileConfiguration cfg = createConfiguration(properties.getProperty("jrsh.config.path"));


            Profile prof = ProfileFactory.getInstance();
            Profile founded = ProfileUtil.find(cfg, cfg.getDefaultProfile());
            ProfileUtil.merge(founded, prof);

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String v : dictionary) {
            Command c = createCommand(v);
            description.put(c.getName(), c.getDescription());
        }
    }
}
