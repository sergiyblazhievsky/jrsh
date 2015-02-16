package com.jaspersoft.jasperserver.shell.exception.server;

/**
 * @author Alexander Krasnyanskiy
 */
public abstract class ServerException extends RuntimeException {
    public ServerException (String message) {
        super(message);
    }
}
