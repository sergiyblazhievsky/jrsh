package com.jaspersoft.jasperserver.shell.exception.server;

/**
 * @author Alexander Krasnyanskiy
 */
public class NoRepositoryContentException extends ServerException {

    public NoRepositoryContentException() {
        super("\rThere is nothing to show. Seems the folder is empty.");
    }
}
