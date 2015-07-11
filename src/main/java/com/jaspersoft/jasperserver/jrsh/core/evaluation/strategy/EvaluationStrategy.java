package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.OperationParser;

public interface EvaluationStrategy {

    OperationResult eval(Script script);

    void setParser(OperationParser parser);

}
