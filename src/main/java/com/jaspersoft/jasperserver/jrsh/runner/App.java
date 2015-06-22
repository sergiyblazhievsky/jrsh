package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.common.ArgumentConverter;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyFactory;
import lombok.val;

/**
 * JRSH's entry point.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public class App {
    public static void main(String[] args) {
        val script = ArgumentConverter.convertToScript(args);
        val strategy = EvaluationStrategyFactory.getStrategy(args);
        strategy.eval(script);
    }
}