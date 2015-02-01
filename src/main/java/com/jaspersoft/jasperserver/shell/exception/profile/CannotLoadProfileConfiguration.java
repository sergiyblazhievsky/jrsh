package com.jaspersoft.jasperserver.shell.exception.profile;

import com.jaspersoft.jasperserver.shell.exception.IOException;

/**
 * @author Alexander Krasnyanskiy
 */
public class CannotLoadProfileConfiguration extends IOException {

    public CannotLoadProfileConfiguration() {
        super("Cannot load profile configuration.");
    }
}
