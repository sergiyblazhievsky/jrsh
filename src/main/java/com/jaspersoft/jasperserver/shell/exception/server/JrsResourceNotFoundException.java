package com.jaspersoft.jasperserver.shell.exception.server;

/**
 * @author Alexander Krasnyanskiy
 */
public class JrsResourceNotFoundException extends ServerException {
    public JrsResourceNotFoundException(String path) {
        super(String.format("There is no such path '%s'", path));
    }
}
