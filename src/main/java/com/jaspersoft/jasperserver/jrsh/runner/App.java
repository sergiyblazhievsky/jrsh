package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.result.OperationResult;

import static com.jaspersoft.jasperserver.jrsh.core.common.ArgumentUtil.convertToScript;

public class App {
    public static void main(String[] args) {
        EvaluationStrategy strategy = EvaluationStrategyFactory
                .getStrategy(args);

        OperationResult result = strategy.eval(
                convertToScript(args)
        );

        System.exit(result
                        .getResultCode()
                        .getValue()
        );
    }
}
