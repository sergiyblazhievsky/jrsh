package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ToolEvaluationStrategy;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;

public class EvaluationStrategyFactory {

    public static EvaluationStrategy getStrategy(String[] arguments) {
        EvaluationStrategy strategy = null;
        Class<? extends EvaluationStrategy> strategyType;

        if (arguments.length == 1
                && isConnectionString(arguments[0])) {
            strategyType = ShellEvaluationStrategy.class;
        }
        else if (arguments.length == 2
                && "--script".equals(arguments[0])
                && isScriptFileName(arguments[1])) {
            strategyType = ScriptEvaluationStrategy.class;
        }
        else {
            strategyType = ToolEvaluationStrategy.class;
        }

        try {
            strategy = strategyType.newInstance();
        } catch (Exception ignored) {
        }

        return strategy;
    }
}
