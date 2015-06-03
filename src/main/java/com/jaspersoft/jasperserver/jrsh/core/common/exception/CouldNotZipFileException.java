package com.jaspersoft.jasperserver.jrsh.core.common.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

/**
 * @author Alex Krasnyanskiy
 */
public class CouldNotZipFileException extends RuntimeException {

    private static final Messages messages = new Messages("i18n/error");

    public CouldNotZipFileException() {
        super(messages.getMessage("message.pack.error"));
    }
}
