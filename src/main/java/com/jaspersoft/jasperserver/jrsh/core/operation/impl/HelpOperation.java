package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import lombok.Data;

@Data
@Master(name = "help", tail = true)
public class HelpOperation implements Operation {
    /*
    @Parameter(dependsOn = "help", values = {
            @Value(tokenAlias = "E", tokenClass = StringToken.class, tail = true, tokenValue = "export"),
            @Value(tokenAlias = "L", tokenClass = StringToken.class, tail = true, tokenValue = "login")
    })
    */

    @Parameter(dependsOn = "help", values =
    @Value(tokenAlias = "CTX", tail = true))
    private String context;

    @Override
    public OperationResult eval(Session session) {
        StringBuilder builder = new StringBuilder();

        /*
         * todo: use locales to featch metadata
         */

        //if (context != null) {
            //Operation operation = OperationFactory.createOperationByName(context);
            //builder.append(getDescription(operation));
        //} else {
            /*
            builder.append("\nUsage (Tool):   jrsh username%password@url <operation> <parameters>\n");
            builder.append("Usage (Shell):  jrsh username%password@url\n");
            builder.append("Usage (Script): jrsh script.jrs\n");
            builder.append("\nAvailable operations: \n");
            for (Operation operation : OperationFactory.createOperationsByAvailableTypes()) {
                builder.append(getDescription(operation)).append("\n");
            }
            */
        //}

        return new OperationResult(builder.toString(), ResultCode.SUCCESS, this, null);
    }

    /*
    protected String getDescription(Operation operation) {
        Master master = operation.getClass().getAnnotation(Master.class);
        return master.description();
    }
    */
}
