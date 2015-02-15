package com.jaspersoft.jasperserver.shell.profile.factory;

import com.jaspersoft.jasperserver.shell.profile.entity.Profile;

/**
 * @author Alexander Krasnyanskiy
 */
@Deprecated
public final class ProfileFactory {

    private static Profile instance;

    private ProfileFactory() {
        // NOP
    }

    public static Profile getInstance() {
        return instance != null ? instance : new Profile();
    }
}
