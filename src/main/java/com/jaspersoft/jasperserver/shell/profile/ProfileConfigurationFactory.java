package com.jaspersoft.jasperserver.shell.profile;

import java.io.FileNotFoundException;

import static com.jaspersoft.jasperserver.shell.profile.ProfileUtil.load;

/**
 * @author Alexander Krasnyanskiy
 */
public final class ProfileConfigurationFactory {

    private static ProfileConfiguration configuration;

    private ProfileConfigurationFactory() {
        // NOP
    }

    public static ProfileConfiguration create(String path) throws FileNotFoundException {
        if (configuration != null) {
            return configuration;
        } else {
            configuration = load(path);
            return configuration;
        }
    }

    public static ProfileConfiguration get(){
        return configuration;
    }
}
