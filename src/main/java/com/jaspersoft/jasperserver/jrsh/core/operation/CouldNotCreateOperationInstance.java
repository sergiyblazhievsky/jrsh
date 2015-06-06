package com.jaspersoft.jasperserver.jrsh.core.operation;

/**
 * @author Alexander Krasnyanskiy
 */
public class CouldNotCreateOperationInstance extends RuntimeException {
    public CouldNotCreateOperationInstance() {
        super("Could not create an operation instance " +
              "(you are probably trying to instantiate an instance of abstract class)");
    }
}
