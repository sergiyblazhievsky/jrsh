package com.jaspersoft.jasperserver.jrsh.core.common.exception;

/**
 * @author Alexander Krasnyanskiy
 */
public class CouldNotZipFileException extends RuntimeException {
    public CouldNotZipFileException() {
        super("Could not pack a directory");
    }
}
