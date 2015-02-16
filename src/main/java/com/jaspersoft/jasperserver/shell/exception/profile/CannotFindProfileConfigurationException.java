package com.jaspersoft.jasperserver.shell.exception.profile;

import com.jaspersoft.jasperserver.shell.exception.InterfaceException;

/**
 * @author Alexander Krasnyanskiy
 */
public class CannotFindProfileConfigurationException extends InterfaceException {

    public CannotFindProfileConfigurationException() {
        super("Cannot load profile configuration.");
    }
}
