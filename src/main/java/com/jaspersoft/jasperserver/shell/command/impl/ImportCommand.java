package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportTaskRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.shell.ExecutionMode;
import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.exception.WrongPathParameterException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.getInstance;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

/**
 * @author Alexander Krasnyanskiy
 */
public class ImportCommand extends Command {

    private List<ImportParameter> params = new ArrayList<>();
    private Session session;

    public ImportCommand() {
        name = "import";
        description = "Import configuration into JasperReportsServer.";
        usageDescription = "\tUsage: import\t<file>\t[with-audit-events] [with-access-events] [with-monitoring-events]\n" + "\t\t\t\t[with-events] [with-update] [with-skip-user-update]";
        parameters.add(new Parameter().setName("anonymous"));

        parameters.add(new Parameter().setName("with-audit-events").setOptional(true));
        parameters.add(new Parameter().setName("with-access-events").setOptional(true));
        parameters.add(new Parameter().setName("with-monitoring-events").setOptional(true));

        parameters.add(new Parameter().setName("with-events").setOptional(true));
        parameters.add(new Parameter().setName("with-update").setOptional(true));
        parameters.add(new Parameter().setName("with-skip-user-update").setOptional(true));
    }

    @Override
    public void run() {

        session = getInstance();
        String path = parameter("anonymous").getValues().get(0);

        if (path.charAt(0) == '"' && path.charAt(path.length() - 1) == '"') {
            path = path.substring(1, path.length() - 1);
        }

        Parameter withAuditEvents = nonAnonymousParameter("with-audit-events");
        Parameter withAccessEvents = nonAnonymousParameter("with-access-events");
        Parameter withMonitoringEvents = nonAnonymousParameter("with-monitoring-events");
        Parameter withUpdate = nonAnonymousParameter("with-update");
        Parameter withSkipUserUpdate = nonAnonymousParameter("with-skip-user-update");


        if (withAuditEvents.isAvailable()) params.add(ImportParameter.INCLUDE_AUDIT_EVENTS);
        if (withAccessEvents.isAvailable()) params.add(ImportParameter.INCLUDE_ACCESS_EVENTS);
        if (withMonitoringEvents.isAvailable()) params.add(ImportParameter.INCLUDE_MONITORING_EVENTS);
        if (withUpdate.isAvailable()) params.add(ImportParameter.UPDATE);
        if (withSkipUserUpdate.isAvailable()) params.add(ImportParameter.SKIP_USER_UPDATE);

        if (getMode().equals(ExecutionMode.SHELL)) {

            Thread spinner = new Thread(new Runnable() {
                @Override
                public void run() {
                    print();
                }
            });
            spinner.setDaemon(true);

            File file = readFile(path);
            ImportTaskRequestAdapter task = session.importService().newTask();

            for (ImportParameter param : params) {
                task.parameter(param, true);
            }

            StateDto state = task.create(file).getEntity();
            spinner.start();
            waitForUpload(state);
            spinner.stop();
            out.printf("\rImport status: SUCCESS\n");
        } else {
            File file = readFile(path);
            ImportTaskRequestAdapter task = session.importService().newTask();
            for (ImportParameter param : params) task.parameter(param, true);
            StateDto state = task.create(file).getEntity();
            waitForUpload(state);
        }
    }

    @SneakyThrows
    private void print() {
        int counter = 0;
        out.print("Importing file");
        while (true) {
            if (counter == 4) {
                counter = 0;
                out.print("\rImporting file");
            }
            out.print(".");
            sleep(250);
            counter++;
        }
    }

    private File readFile(String path) {
        File file;
        try {
            file = new File(path);
            if (!file.exists() || file.isDirectory()) throw new FileNotFoundException();
        } catch (Exception e) {
            throw new WrongPathParameterException();
        }
        return file;
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
