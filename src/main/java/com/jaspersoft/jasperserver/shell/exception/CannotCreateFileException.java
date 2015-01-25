package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class CannotCreateFileException extends IOException {

    public CannotCreateFileException() {
        super("Cannot create file.");
    }
}
