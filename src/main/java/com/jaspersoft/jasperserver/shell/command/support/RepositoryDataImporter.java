package com.jaspersoft.jasperserver.shell.command.support;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportTaskRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import lombok.SneakyThrows;

import java.io.InputStream;

import static java.lang.Thread.sleep;

/**
 * @author Alexander Krasnyanskiy
 */
public class RepositoryDataImporter {

    private Session session;

    public RepositoryDataImporter(Session session) {
        this.session = session;
    }

    public void importData(InputStream data) {
        ImportTaskRequestAdapter task = session.importService().newTask();
        StateDto state = task.create(data).getEntity();
        waitForUpload(state);
    }

    @SneakyThrows
    private void waitForUpload(StateDto state) {
        do {
            if ("finished".equals(getPhase(state))) break;
            sleep(350);
        } while (true);
    }

    private String getPhase(StateDto state) {
        return session.exportService().task(state.getId()).state().getEntity().getPhase();
    }
}
