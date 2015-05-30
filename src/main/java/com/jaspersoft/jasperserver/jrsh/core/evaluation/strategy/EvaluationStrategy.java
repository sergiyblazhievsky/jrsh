package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;

/**
 * @author Alex Krasnyanskiy
 */
public interface EvaluationStrategy {

    OperationResult eval(Script script);

}