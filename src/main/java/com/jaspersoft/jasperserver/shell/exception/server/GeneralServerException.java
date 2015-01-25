package com.jaspersoft.jasperserver.shell.exception.server;

/**
 * @author Alexander Krasnyanskiy
 */
public class GeneralServerException extends ServerException {

    public GeneralServerException(String message) {
        super(String.format("server error: %s", message));
    }
}
