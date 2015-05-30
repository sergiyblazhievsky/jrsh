package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ToolEvaluationStrategy;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;

/**
 * Helper class that identifies a suitable strategy
 * for the operation processing.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class EvaluationStrategyTypeIdentifier {

    /**
     * Identifies a suitable strategy
     *
     * @param args app arguments
     * @return strategy
     */
    public static Class<? extends EvaluationStrategy> identifyType(String[] args) {
        Class<? extends EvaluationStrategy> strategyType;
        if (args.length == 1 && isConnectionString(args[0])) {
            strategyType = ShellEvaluationStrategy.class;
        } else if (args.length == 2 && "--script".equals(args[0])
                && isScriptFileName(args[1])) {
            strategyType = ScriptEvaluationStrategy.class;
        } else {
            strategyType = ToolEvaluationStrategy.class;
        }
        return strategyType;
    }
}
