package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;

@Master(name = "for_test")
public class TestOperation implements Operation {
    @Override
    public OperationResult eval(Session session) {
        return null;
    }
}