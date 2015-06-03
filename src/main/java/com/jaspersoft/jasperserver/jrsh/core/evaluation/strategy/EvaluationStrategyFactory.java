package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.WrongStrategyTypeException;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ToolEvaluationStrategy;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;

/**
 * @author Alexander Krasnyanskiy
 */
public class EvaluationStrategyFactory {

    /**
     * Create evaluation strategy.
     *
     * @param args app arguments
     * @return strategy
     */
    public static EvaluationStrategy getStrategy(String[] args) {
        //
        // Define strategy type based on application arguments
        //
        Class<? extends EvaluationStrategy> strategyType;
        if (args.length == 1 && isConnectionString(args[0])) {
            strategyType = ShellEvaluationStrategy.class;
        } else if (args.length == 2 && "--script".equals(args[0])
                && isScriptFileName(args[1])) {
            strategyType = ScriptEvaluationStrategy.class;
        } else {
            strategyType = ToolEvaluationStrategy.class;
        }
        //
        // Create strategy instance
        //
        try {
            return strategyType.newInstance();
        } catch (InstantiationException | IllegalAccessException unimportant) {
            throw new WrongStrategyTypeException();
        }
    }
}