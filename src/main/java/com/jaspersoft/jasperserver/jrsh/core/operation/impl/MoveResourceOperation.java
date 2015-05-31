package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.RepositoryPathToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.FAILED;
import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.SUCCESS;

@Data
@Master(name = "mv-res", description = "Move resources.")
public class MoveResourceOperation implements Operation {

    @Parameter(mandatory = true, dependsOn = "mv-res", values =
    @Value(tokenAlias = "R", tokenClass = RepositoryPathToken.class))
    private String resource;

    @Parameter(mandatory = true, dependsOn = "R", values =
    @Value(tokenAlias = "TF", tokenValue = "to-folder", tokenClass = StringToken.class))
    private String direction;

    @Parameter(mandatory = true, dependsOn = "TF", values =
    @Value(tokenAlias = "FOLD", tail = true, tokenClass = RepositoryPathToken.class))
    private String folder;

    @Override
    public OperationResult eval(Session session) {
        OperationResult result;
        try {
            ClientResource res = session
                    .resourcesService()
                    .resource(folder)
                    .moveFrom(resource)
                    .getEntity();
            String date = res.getCreationDate();
            result = new OperationResult("Moved on " + date, SUCCESS, this, null);
        } catch (Exception e) {
            result = new OperationResult("Move resource failed.", FAILED, this, null);
        }

        return result;
    }
}