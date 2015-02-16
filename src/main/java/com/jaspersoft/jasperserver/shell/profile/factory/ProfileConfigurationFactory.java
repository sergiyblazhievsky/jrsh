package com.jaspersoft.jasperserver.shell.profile.factory;

import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.reader.ProfileReader;

import java.io.FileNotFoundException;


/**
 * @author Alexander Krasnyanskiy
 */
@Deprecated
public final class ProfileConfigurationFactory {

    private static ProfileConfiguration configuration;

    private ProfileConfigurationFactory() {
        // NOP
    }

    public static ProfileConfiguration createConfiguration(String file) throws FileNotFoundException {
        if (configuration != null) {
            return configuration;
        } else {
            configuration = new ProfileReader(file).read();
            return configuration;
        }
    }

    public static ProfileConfiguration getConfiguration(){
        return configuration;
    }
}
