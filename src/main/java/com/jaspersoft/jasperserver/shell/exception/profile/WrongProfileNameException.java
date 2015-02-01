package com.jaspersoft.jasperserver.shell.exception.profile;

import com.jaspersoft.jasperserver.shell.exception.InterfaceException;

/**
 * @author Alexander Krasnyanskiy
 */
public class WrongProfileNameException extends InterfaceException {

    public WrongProfileNameException() {
        super("Cannot find the profile with specified name.");
    }
}
