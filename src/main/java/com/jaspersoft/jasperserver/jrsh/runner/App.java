package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.common.ArgumentConverter;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyFactory;
import lombok.extern.log4j.Log4j;

/**
 * @author Alexander Krasnyanskiy
 */
@Log4j
public class App {
    public static void main(String[] args) {
        Script script = ArgumentConverter.convertToScript(args);
        EvaluationStrategy strategy = EvaluationStrategyFactory.getStrategy(args);
        strategy.eval(script);
    }
}