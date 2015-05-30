package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jrsh.core.common.ZipUtil;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.FileNameToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.FAILED;
import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.SUCCESS;

@Data
@Log4j
@Master(name = "import", description = "This is an import")
public class ImportOperation implements Operation {

    @Parameter(mandatory = true, dependsOn = "import", values = {
            @Value(tokenAlias = "ZP", tokenClass = StringToken.class, tokenValue = "zip"),
            @Value(tokenAlias = "DIR", tokenClass = StringToken.class, tokenValue = "directory")
    })
    private String context;

    @Parameter(mandatory = true, dependsOn = {"ZP", "DIR"}, values =
    @Value(tail = true, tokenClass = FileNameToken.class, tokenAlias = "PTH"))
    private String path;

    @Override
    public OperationResult eval(Session session) {
        OperationResult result;
        try {
            if ("zip".equals(context)) {
                StateDto entity = session.importService().newTask().create(new File(path)).getEntity();
                upload(entity, session);
                result = new OperationResult("Import is done", SUCCESS, this, null);
            } else if ("directory".equals(context)) {
                File importFile = ZipUtil.pack(path);
                StateDto entity = session.importService().newTask().create(importFile).getEntity();
                String phase = upload(entity, session);
                //
                // Clean up zip file
                //
                if (importFile.exists()) {
                    importFile.delete();
                }
                //
                // Check task phase
                //
                switch (phase) {
                    case "finished":
                        result = new OperationResult("Import is done", SUCCESS, this, null);
                        break;
                    default:
                        // failed
                        result = new OperationResult("Import failed", FAILED, this, null);
                        break;
                }
            } else {
                result = new OperationResult("Import error: wrong context", FAILED, this, null);
            }
        } catch (Exception e) {
            result = new OperationResult(String.format("Import failed (%s)", e.getMessage()), FAILED, this, null);
        }
        return result;
    }

    /**
     * Waits till the task finish its work.
     *
     * @param state   task state
     * @param session session
     */
    protected String upload(StateDto state, Session session) {
        String phase;
        while (true) {
            phase = getPhase(state, session);
            if ("finished".equals(phase) || "failed".equals(phase)) {
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException ignored) {
                break;
            }
        }
        return phase;
    }

    /**
     * Returns the phase of the current running task.
     *
     * @param state   task state
     * @param session session
     * @return phase
     */
    protected String getPhase(StateDto state, Session session) {
        return session.exportService()
                .task(state.getId())
                .state()
                .getEntity()
                .getPhase();
    }
}



