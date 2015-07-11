package com.jaspersoft.jasperserver.jrsh.core.common.exception;

public class DirectoryDoesNotExistException extends RuntimeException {
    public DirectoryDoesNotExistException(String directory) {
        super(String.format("Directory %s does not exist", directory));
    }
}
