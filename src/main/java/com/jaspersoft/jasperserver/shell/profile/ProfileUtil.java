package com.jaspersoft.jasperserver.shell.profile;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileUtil {

    private final static Yaml YML;

    static {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setAllowReadOnlyProperties(true);
        YML = new Yaml(options);
    }

    public static ProfileConfiguration load(String path) throws FileNotFoundException {
        Map config = YML.loadAs(new FileReader(path), Map.class);
        return convert(config);
    }

    private static ProfileConfiguration convert(Object config) {
        List rawData = (List) ((Map) config).get("profiles");
        ProfileConfiguration profiles = new ProfileConfiguration();

        for (Object o : rawData) {
            String profileName = (String) ((Map) o).get("name");
            String serverUrl = (String) ((Map) o).get("url");
            String username = (String) ((Map) o).get("username");
            String org = (String) ((Map) o).get("organization");
            profiles.getProfiles().add(new Profile().setName(profileName).setOrganization(org).setUrl(serverUrl)
                    .setUsername(username));
        }
        return profiles;
    }

    public static void persist(ProfileConfiguration configuration, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        YML.dump(configuration, writer);
        writer.flush();
        writer.close();
    }

    public static boolean isEmpty(Profile profile) {
        return profile.getUrl() == null && profile.getUsername() == null && profile.getOrganization() == null;
    }
}
