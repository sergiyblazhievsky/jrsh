package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

/**
 * @author Alex Krasnyanskiy
 */
public class CouldNotCreateOperationInstance extends RuntimeException {

    private static final Messages messages = new Messages("i18n/error");

    public CouldNotCreateOperationInstance() {
        super(messages.getMessage("message.operation.instantiation.error"));
    }
}
