package com.jaspersoft.jasperserver.jrsh.core.common.exception;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

import static java.lang.String.format;

/**
 * @author Alex Krasnyanskiy
 */
public class DirectoryDoesNotExistException extends RuntimeException {

    private static final Messages messages = new Messages("i18n/error");

    public DirectoryDoesNotExistException(String directory) {
        super(format(messages.getMessage("message.directory.does.not.exist"), directory));
    }
}
