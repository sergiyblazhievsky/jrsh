package com.jaspersoft.jasperserver.jrsh

.core.operation;

public class CouldNotCreateOperationInstance extends RuntimeException {
    public CouldNotCreateOperationInstance(Exception err) {
        super(String.format("Could not create an operation instance (%s)", err.getMessage()));
    }
}
