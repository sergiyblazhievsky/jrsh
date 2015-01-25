package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionListWrapper;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;

/**
 * @author Alexander Krasnyanskiy
 */
public class TestCommand extends Command {

    public TestCommand() {
        name = "test";
        description = "For test purpose only.";
        parameters.add(new Parameter().setName("param1").setOptional(true));
        parameters.add(new Parameter().setName("param2").setOptional(true));
        parameters.add(new Parameter().setName("param3").setOptional(true));
    }

    @Override
    void run() {
        OperationResult<ReportExecutionListWrapper> result = SessionFactory.getInstance()
                .reportingService().runningReportsAndJobs()
                .find();

        result.getEntity();
        System.out.println("Done!");
    }
}
