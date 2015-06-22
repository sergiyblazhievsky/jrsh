package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.RepositoryToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.VirtualRepositoryToken;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceServiceParameter.CREATE_FOLDERS;

@Data
@Master(name = "copy",
        description = "Copy resources on JR server",
        usage = "copy [path/to/resource] to [path/to/folder]")
public class CopyResourceOperation implements Operation {

    @Parameter(mandatory = true, dependsOn = "copy", values =
    @Value(tokenAlias = "R_", tokenClass = RepositoryToken.class))
    private String resource;
    @Parameter(mandatory = true, dependsOn = "R_", values =
    @Value(tokenAlias = "TO_", tokenValue = "to", tokenClass = StringToken.class))
    private String direction;
    @Parameter(mandatory = true, dependsOn = "TO_", values =
    @Value(tokenAlias = "FLDR", tail = true, tokenClass = VirtualRepositoryToken.class))
    private String folder;

    // TODO add filter to make operation copy resources by mask

    @Override
    public OperationResult execute(Session session) {
        OperationResult result = null;
        try {
            // check if `resource` contains mask
            /*
                1. /public/Samples/Reports/*DailyReport
                2. /public/Samples/Reports/WeeklyReportOf*
             */
            ClientResource response = session.resourcesService()
                    .resource(folder)
                    .parameter(CREATE_FOLDERS, "true")
                    .copyFrom(resource)
                    .getEntity();

            String date = response.getUpdateDate();
            String uri = response.getUri();
            String __resource = StringUtils.substringAfterLast(uri, "/");
            result = new OperationResult("Copied: " + __resource + " (" + date + ")", ResultCode.SUCCESS, this, null);
        } catch (Exception e) {
            result = new OperationResult("Failed (" + e.getMessage() + ")", ResultCode.FAILED, this, null);
        }
        return result;
    }
}
