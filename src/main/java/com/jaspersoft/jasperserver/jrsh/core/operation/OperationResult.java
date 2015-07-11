package com.jaspersoft.jasperserver.jrsh.core.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class OperationResult {
    private String resultMessage;
    private ResultCode resultCode;
    private Operation context;
    private OperationResult previous;
}
