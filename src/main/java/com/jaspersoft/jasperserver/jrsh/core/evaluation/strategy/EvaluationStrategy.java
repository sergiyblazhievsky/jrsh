package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.operation.result.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.OperationParser;

import java.util.List;

public interface EvaluationStrategy {

    OperationResult eval(List<String> source);

    void setParser(OperationParser parser);

}
