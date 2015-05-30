package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.FileNameToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;

import java.io.File;

@Data
@Master(name = "import", description = "This is an import.")
public class ImportOperation implements Operation {

    @Parameter(mandatory = true, dependsOn = "import", values = {
            @Value(tokenAlias = "ZP_", tokenClass = StringToken.class, tokenValue = "zip"),
            @Value(tokenAlias = "DIR_", tokenClass = StringToken.class, tokenValue = "directory")
    })
    private String context;

    @Parameter(mandatory = true, dependsOn = {"ZP_", "DIR_"}, values =
    @Value(tail = true, tokenClass = FileNameToken.class, tokenAlias = "PTH"))
    private String path;

    @Override
    public OperationResult eval(Session session) {
        OperationResult result = null;
        try {

            if ("zip".equals(context)){
                StateDto entity = session.importService().newTask().create(new File(path)).getEntity();
                result = new OperationResult("Import started...", ResultCode.SUCCESS, this, null);
            } else {
                result = new OperationResult("Error", ResultCode.FAILED, this, null);
            }
        } catch (Exception e){
            result = new OperationResult("Failed.", ResultCode.FAILED, this, null);
        }

        return result;
    }
}



