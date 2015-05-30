package com.jaspersoft.jasperserver.jrsh.core.evaluation;

public class WrongStrategyTypeException extends RuntimeException {
    public WrongStrategyTypeException() {
        super("Could not create a strategy instance");
    }
}
