package com.jaspersoft.jasperserver.shell.exception.profile;

import com.jaspersoft.jasperserver.shell.exception.InterfaceException;

/**
 * @author Alexander Krasnyanskiy
 */
public class NotUniqueProfileNameException extends InterfaceException {

    public NotUniqueProfileNameException() {
        super("You've specified not unique name for profile.");
    }
}
