package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Set;

import static com.jaspersoft.jasperserver.jrsh.core.operation.ResultCode.SUCCESS;

@Data
@Master(name = "help", tail = true, usage = "help [operation]", description = "Operation <help> demonstrates how you can use cli")
public class HelpOperation implements Operation {

    public static final String PREFIX = "   ";

    @Override
    public OperationResult execute(Session session) {
        StringBuilder builder = new StringBuilder("\nHow to use\n");
        Set<Operation> operations = OperationFactory.createOperationsByAvailableTypes();
        for (Operation operation : operations) {

            Master master = operation.getClass().getAnnotation(Master.class);

            Field field;
            String description;
            String usage;

            if (master != null) {
                description = master.description();
                usage = master.usage();
                builder
                        .append(PREFIX)
                        .append(description)
                        .append("\n")
                        .append(PREFIX)
                        .append("usage: ")
                        .append(usage)
                        .append("\n\n");
            }
        }

        return new OperationResult(builder.toString(), SUCCESS, this, null);
    }
}
