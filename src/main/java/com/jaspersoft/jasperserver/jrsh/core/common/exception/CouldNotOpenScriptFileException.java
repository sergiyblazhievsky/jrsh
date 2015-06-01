package com.jaspersoft.jasperserver.jrsh.core.common.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

public class CouldNotOpenScriptFileException extends RuntimeException {

    private static final Messages messages = new Messages("i18n/error");

    public CouldNotOpenScriptFileException(String file) {
        super(messages.getMessage("message.script.file.error") + file);
    }
}
