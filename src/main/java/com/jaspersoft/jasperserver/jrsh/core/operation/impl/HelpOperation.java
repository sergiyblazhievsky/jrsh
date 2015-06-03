package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Set;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.SUCCESS;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Master(name = "help", tail = true)
public class HelpOperation implements Operation {

    public static final String PREFIX = "   ";
    private Messages messages = new Messages("i18n/help");

    @Override
    public OperationResult eval(Session session) {
        StringBuilder builder = new StringBuilder("\nAvailable operations:\n");

        Set<Operation> operations = OperationFactory.createOperationsByAvailableTypes();
        for (Operation operation : operations) {
            Field field = null;
            try {
                field = operation.getClass().getDeclaredField("messages");
                field.setAccessible(true);
                Messages m = (Messages) field.get(operation);
                //
                // Get description & usage
                //
                String description = m.getMessage("description");
                String usage = m.getMessage("usage");
                //
                // Build message
                //
                builder
                        .append(PREFIX)
                        .append(description)
                        .append("\n")
                        .append(PREFIX)
                        .append(usage)
                        .append("\n\n");
                //
                // Restore accessible flag for the object
                //
                field.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return new OperationResult(builder.toString(), SUCCESS, this, null);
    }
}
