package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.common.ArgumentConverter;
import com.jaspersoft.jasperserver.jrsh.core.common.Data;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyFactory;
import lombok.extern.log4j.Log4j;

/**
 * @author Alex Krasnyanskiy
 */
@Log4j
public class App {
    public static void main(String[] args) {
        Data data = ArgumentConverter.convertToData(args);
        EvaluationStrategy strategy = EvaluationStrategyFactory.getStrategy(args);
        strategy.eval(data);
    }
}