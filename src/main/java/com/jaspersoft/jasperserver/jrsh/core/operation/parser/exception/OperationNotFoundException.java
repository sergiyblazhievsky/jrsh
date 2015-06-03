package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

/**
 * @author Alex Krasnyanskiy
 */
public class OperationNotFoundException extends OperationParseException {

    private static final Messages messages = new Messages("i18n/error");

    public OperationNotFoundException() {
        super(messages.getMessage("message.operation.not.found"));
    }
}
