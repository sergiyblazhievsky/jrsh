package com.jaspersoft.jasperserver.shell.profile.reader;

import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.representer.SkipEmptyRepresenter;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static org.yaml.snakeyaml.DumperOptions.FlowStyle.BLOCK;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileReader {

    private Yaml yml;
    private String file;

    public ProfileReader() {
        init();
    }

    public ProfileReader(String file) {
        this.file = file;
        init();
    }

    private void init() {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(BLOCK);
        yml = new Yaml(new SkipEmptyRepresenter(), options);
    }

    @SneakyThrows
    public ProfileConfiguration read(String file) {
        File ymlFile = new File(file);
        FileInputStream stream = new FileInputStream(ymlFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Map config = yml.loadAs(reader, Map.class);
        return convert(config);
    }

    @SneakyThrows
    public ProfileConfiguration read() {
        File ymlFile = new File(file);
        FileInputStream stream = new FileInputStream(ymlFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Map config = yml.loadAs(reader, Map.class);
        if (config == null) return new ProfileConfiguration();
        return convert(config);
    }

    private static ProfileConfiguration convert(Map config) {
        ProfileConfiguration cfg = new ProfileConfiguration();
        List data = (List) config.get("profiles");
        String def = (String) config.get("default");
        cfg.setDefaultProfile(def);
        if (data != null && !data.isEmpty()) {
            for (Object o : data) {
                String name = (String) ((Map) o).get("name");
                String url = (String) ((Map) o).get("url");
                String user = (String) ((Map) o).get("username");
                String org = (String) ((Map) o).get("organization");
                Profile profile = new Profile(name, url, user, org);
                cfg.getProfiles().add(profile);
            }
        }
        return cfg;
    }
}
