package com.jaspersoft.jasperserver.shell.exception.profile;

import com.jaspersoft.jasperserver.shell.exception.IOException;

/**
 * @author Alexander Krasnyanskiy
 */
public class CannotSaveProfileException extends IOException {

    public CannotSaveProfileException() {
        super("Cannot save profile.");
    }
}
