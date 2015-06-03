package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;

public class WrongStrategyTypeException extends RuntimeException {

    private static final Messages messages = new Messages("i18n/error");

    public WrongStrategyTypeException() {
        super(messages.getMessage("message.strategy.instantiation.error"));
    }
}
