package com.jaspersoft.jasperserver.shell.command.export;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;

import java.io.InputStream;

import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
public class RepositoryDataExporter {

    private Session session;

    public RepositoryDataExporter(Session session) {
        this.session = session;
    }

    public InputStream export() {
        ExportTaskAdapter task = session.exportService().newTask();
        StateDto state = task.parameters(asList(ExportParameter.EVERYTHING)).create().getEntity();
        return session.exportService().task(state.getId()).fetch().getEntity();
    }
}
