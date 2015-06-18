package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ToolEvaluationStrategy;
import lombok.extern.log4j.Log4j;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;

/**
 * @author Alexander Krasnyanskiy
 */
@Log4j
public class EvaluationStrategyFactory {

    public static EvaluationStrategy getStrategy(String[] args) {
        EvaluationStrategy strategy = null;
        Class<? extends EvaluationStrategy> strategyType;
        //
        // Define strategy type
        //
        if (args.length == 1 && isConnectionString(args[0])) {
            strategyType = ShellEvaluationStrategy.class;
        }
        else if (args.length == 2 && "--script".equals(args[0]) && isScriptFileName(args[1])) {
            strategyType = ScriptEvaluationStrategy.class;
        }
        else {
            strategyType = ToolEvaluationStrategy.class;
        }

        try {
            strategy = strategyType.newInstance();
        } catch (InstantiationException ignored) {
            log.info(String.format("Cannot create strategy instance of [%s] type", strategyType));
        } catch (IllegalAccessException ignored) {
            log.info(String.format("Cannot create strategy instance of [%s] type", strategyType));
        }

        return strategy;
    }
}