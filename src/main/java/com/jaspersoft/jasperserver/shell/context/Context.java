package com.jaspersoft.jasperserver.shell.context;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.profile.ProfileUtil;
import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.factory.ProfileFactory;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.createCommand;
import static com.jaspersoft.jasperserver.shell.profile.ProfileUtil.find;
import static com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory.createConfiguration;
import static java.util.Arrays.asList;

@Data
@Deprecated
public class Context {

    private Map<String, String> cmdDescription = new HashMap<>();
    private Properties properties;
    private List<String> dictionary
            = new ArrayList<>(asList("help", "?", "import", "replicate", "export",
            "profile", "session", "logout", "login", "exit", "show", "clear"));

    public Context() {
        try {
            initProperties();
            ProfileConfiguration cfg = createConfiguration(properties.getProperty("jrsh.config.path"));
            if (cfg != null) {
                Profile profile = ProfileFactory.getInstance();
                ProfileUtil.copy(profile, find(cfg, cfg.getDefaultProfile()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String v : dictionary) {
            Command c = createCommand(v);
            cmdDescription.put(c.getName(), c.getDescription());
        }
    }

    private void initProperties() {
        properties = new Properties();
        InputStream stream = Context.class.getClass().getResourceAsStream("/context.properties");
        try {
            properties.load(stream);
        } catch (IOException ignored) {/* NOP */}
    }
}
