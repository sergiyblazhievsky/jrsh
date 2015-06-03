package com.jaspersoft.jasperserver.jrsh.core.common.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

public class CouldNotCreateJLineConsoleException extends RuntimeException {

    private static final Messages messages = new Messages("i18n/error");

    public CouldNotCreateJLineConsoleException() {
        super(messages.getMessage("message.jline.console.error"));
    }
}
