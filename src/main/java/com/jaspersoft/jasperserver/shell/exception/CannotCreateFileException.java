package com.jaspersoft.jasperserver.shell.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class CannotCreateFileException extends IOException {

    public CannotCreateFileException() {
        super("i/o error: Cannot create file.");
    }
}
