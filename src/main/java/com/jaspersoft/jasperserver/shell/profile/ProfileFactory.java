package com.jaspersoft.jasperserver.shell.profile;

/**
 * @author Alexander Krasnyanskiy
 */
public final class ProfileFactory {

    private static Profile instance;

    private ProfileFactory() {
        // NOP
    }

    public static Profile getInstance() {
        return instance != null ? instance : new Profile();
    }
}
