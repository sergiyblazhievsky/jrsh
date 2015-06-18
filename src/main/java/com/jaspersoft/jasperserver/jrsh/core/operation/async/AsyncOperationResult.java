package com.jaspersoft.jasperserver.jrsh.core.operation.async;

import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AsyncOperationResult extends OperationResult {

    private String resultMessage;
    private ResultCode resultCode;
    private AsyncOperation context;
    private OperationResult previous;

    private long runtime;
    private long latency;
    private long attemptsCount;
}
