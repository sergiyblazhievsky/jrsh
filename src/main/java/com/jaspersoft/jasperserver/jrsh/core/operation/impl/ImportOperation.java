package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jrsh.core.common.ZipUtil;
import com.jaspersoft.jasperserver.jrsh.core.i18n.Messages;
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
@Master(name = "import")
public class ImportOperation implements Operation {

    private Messages messages = new Messages("i18n/import");

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
        //
        // Get operation messages
        //
        String ok = messages.getMessage("messages.success");
        String failed = messages.getMessage("messages.fail");
        String formattedOK = messages.getMessage("messages.format.fail");
        String ioWarning = messages.getMessage("messages.io.warning");
        //
        // Import zip/directory
        //
        OperationResult result;
        try {
            if ("zip".equals(context)) {
                //
                // Upload resources
                //
                StateDto entity = session.importService()
                        .newTask()
                        .create(new File(path))
                        .getEntity();
                //
                // Wait until execution is complete
                //
                wait(entity, session);
                result = new OperationResult(ok, SUCCESS, this, null);
            } else if ("directory".equals(context)) {
                File importFile = ZipUtil.pack(path);
                StateDto entity = session.importService().newTask().create(importFile).getEntity();
                String phase = wait(entity, session);
                //
                // Clean up zip file
                //
                if (importFile.exists()) {
                    //
                    // Delete temporary zip file
                    //
                    boolean isDeleted = importFile.delete();
                    if (!isDeleted) {
                        log.info(ioWarning);
                    }
                }
                //
                // Check task phase
                //
                switch (phase) {
                    case "finished":
                        result = new OperationResult(ok, SUCCESS, this, null);
                        break;
                    default:
                        //
                        // Failed
                        //
                        result = new OperationResult(failed, FAILED, this, null);
                        break;
                }
            } else {
                result = new OperationResult(String.format(formattedOK, context), FAILED, this, null);
            }
        } catch (Exception e) {
            result = new OperationResult(String.format(formattedOK, e.getMessage()), FAILED, this, null);
        }
        return result;
    }

    /**
     * Waits till the task finish its work.
     *
     * @param state   task state
     * @param session session
     */
    protected String wait(StateDto state, Session session) {
        String phase;
        while (true) {
            phase = getPhase(state, session);
            if ("finished".equals(phase) || "failed".equals(phase)) {
                break;
            }
            try {
                //
                // Wait a quarter of second
                //
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



