package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

import static java.lang.String.format;

public class CannotFindSetterException extends OperationParseException {

    private static final Messages messages = new Messages("i18n/error");

    public CannotFindSetterException(String msg) {
        super(format(messages.getMessage("message.setter.does.not.exist"), msg));
    }
}
