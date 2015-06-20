package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.OperationParser;

/**
 * Base interface for every JRSH evaluation strategy.
 * Basically, the strategy represents an algorithm of
 * script evaluation.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public interface EvaluationStrategy {

    /**
     * Evaluates the script.
     *
     * @param script sequence of operations
     * @return result
     */
    OperationResult eval(Script script);

    /**
     * Add operation parser to strategy.
     *
     * @param parser parser
     */
    void setParser(OperationParser parser);

}