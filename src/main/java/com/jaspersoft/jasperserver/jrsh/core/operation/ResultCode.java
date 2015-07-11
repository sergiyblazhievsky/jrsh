package com.jaspersoft.jasperserver.jrsh.core.operation;

public enum ResultCode {
    SUCCESS(0),
    FAILED(1),
    INTERRUPTED(2);

    private int value;
    
    ResultCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
