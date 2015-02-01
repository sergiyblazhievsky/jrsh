package com.jaspersoft.jasperserver.shell.command.exp;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;

import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class RepositoryDataExporter {

    public InputStream export() {
        ExportTaskAdapter task = SessionFactory.getInstance().exportService().newTask();
        //setExportOptions(task, user, role, path);
        //StateDto state = task.parameters(interParams).create().getEntity();
        return null;
    }

    private void setExportOptions(ExportTaskAdapter task, String user, String role, String repo) {
        if (role != null) {
            task.role(role);
            return;
        }
        if (user != null) {
            task.user(user);
            return;
        }
        if (repo != null) {
            task.uri(repo);
        }
    }
}
