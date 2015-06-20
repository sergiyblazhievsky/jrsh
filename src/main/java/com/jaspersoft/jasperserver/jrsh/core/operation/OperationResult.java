package com.jaspersoft.jasperserver.jrsh.core.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The result of multiple operations. It has a chained structure,
 * because each operation result points to the previous one, thus
 * creating a chain of results.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class OperationResult {

    private String resultMessage;
    private ResultCode resultCode;
    private Operation context;
    private OperationResult previous;

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
}