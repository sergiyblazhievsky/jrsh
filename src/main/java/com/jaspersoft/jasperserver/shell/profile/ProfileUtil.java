package com.jaspersoft.jasperserver.shell.profile;

import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.reader.ProfileReader;
import com.jaspersoft.jasperserver.shell.profile.writer.ProfileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileUtil {

    public static ProfileConfiguration load(String path) throws FileNotFoundException {
        return new ProfileReader(path).read();
    }

    public static void persist(ProfileConfiguration configuration, String path) throws IOException {
        new ProfileWriter(path).write(configuration);
    }

    public static boolean isEmpty(Profile profile) {
        return profile.getUrl() == null && profile.getUsername() == null && profile.getOrganization() == null;
    }

    public static Profile find(ProfileConfiguration cfg, String name) {
        for (Profile p : cfg.getProfiles()) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

}
