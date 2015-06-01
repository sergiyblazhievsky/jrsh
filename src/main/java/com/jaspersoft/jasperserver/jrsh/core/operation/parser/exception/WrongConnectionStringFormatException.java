package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

public class WrongConnectionStringFormatException extends OperationParseException {

    private static final Messages messages = new Messages("i18n/error");

    public WrongConnectionStringFormatException() {
        super(messages.getMessage("message.wrong.connection.string"));
    }
}
