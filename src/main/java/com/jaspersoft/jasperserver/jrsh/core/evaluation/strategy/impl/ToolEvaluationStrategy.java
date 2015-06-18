package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;

import java.util.Collection;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory.createOperationByName;
import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.FAILED;

/**
 * @author Alexander Krasnyanskiy
 */
public class ToolEvaluationStrategy extends AbstractEvaluationStrategy {

    @Override
    public OperationResult eval(Script script) {
        Collection<String> operations = script.getSource();
        Operation operationInstance = null;
        OperationResult result = null;
        try {
            for (String operation : operations) {
                Session session = SessionFactory.getSharedSession();
                operationInstance = parser.parse(operation);
                OperationResult temp = result;
                result = operationInstance.eval(session);

                System.out.println(result.getResultMessage());
                result.setPrevious(temp);
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            //
            // Print help
            //
            Operation help = createOperationByName("help");
            System.out.println(help.eval(null).getResultMessage());
            //
            // Prepare operation result
            //
            if (result != null) {
                result = new OperationResult(error.getMessage(), FAILED, operationInstance, result);
            } else {
                result = new OperationResult(error.getMessage(), FAILED, operationInstance, null);
            }
        }
        return result;
    }

}