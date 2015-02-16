package com.jaspersoft.jasperserver.shell.exception.profile;

import com.jaspersoft.jasperserver.shell.exception.InterfaceException;

/**
 * @author Alexander Krasnyanskiy
 */
public class CurrentProfileIsNotAvailableException extends InterfaceException {

    public CurrentProfileIsNotAvailableException() {
        super("There is no active profile. You should login to session a profile or session it manually.");
    }
}
