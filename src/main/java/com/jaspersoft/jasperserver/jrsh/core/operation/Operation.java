package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.result.OperationResult;

public interface Operation {

    OperationResult execute(Session session);

}
