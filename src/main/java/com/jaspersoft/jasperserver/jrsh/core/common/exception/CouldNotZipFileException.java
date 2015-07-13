package com.jaspersoft.jasperserver.jrsh.core.common.exception;

public class CouldNotZipFileException extends RuntimeException {
    public CouldNotZipFileException() {
        super("Could not pack a directory");
    }
}
