package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.shell.ExecutionMode;
import com.jaspersoft.jasperserver.shell.exception.CannotCreateFileException;
import com.jaspersoft.jasperserver.shell.exception.SessionIsNotAvailableException;
import com.jaspersoft.jasperserver.shell.exception.UnspecifiedRoleException;
import com.jaspersoft.jasperserver.shell.exception.UnspecifiedUserNameException;
import com.jaspersoft.jasperserver.shell.exception.parser.ParameterValueSizeException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.EVERYTHING;
import static com.jaspersoft.jasperserver.shell.ExecutionMode.SHELL;
import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.create;
import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.getInstance;
import static java.lang.String.format;
import static java.lang.System.exit;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;
import static sun.misc.IOUtils.readFully;


/**
 * @author Alexander Krasnyanskiy
 */
@Log
public class ExportCommand extends Command {

    private List<ExportParameter> interParams = new ArrayList<>();

    public ExportCommand() {
        name = "export";
        description = "Exports configuration of JasperReportsServer.";
        parameters.add(new Parameter().setName("anonymous")/*.setOptional(true)*/.setMultiple(true));
        parameters.add(new Parameter().setName("to").setOptional(true));
        parameters.add(new Parameter().setName("without-access-events").setOptional(true));
        parameters.add(new Parameter().setName("without-audit-events").setOptional(true));
        parameters.add(new Parameter().setName("without-monitoring-events").setOptional(true));
        parameters.add(new Parameter().setName("without-events").setOptional(true));
        parameters.add(new Parameter().setName("without-users-and-roles").setOptional(true));
    }

    @Override
    void run() {
        Session session = getInstance();
        String path; // or may be a command
        String to   = null;
        String role = null;
        String user = null;

        List<String> values = parameter("anonymous").getValues();

        if (!values.isEmpty()) {
            path = values.get(0);
            if (values.size() > 1) {
                to = values.get(1);
            }
            if (values.size() > 2) {
                throw new ParameterValueSizeException("?", "export");
            }
        } else {
            Command cmd = create("help");
            cmd.parameter("anonymous").setValues(asList("export"));
            cmd.run();
            return;
        }

        switch (path) {
            case "all":
                interParams.add(EVERYTHING);
                break;
            case "role":
                if (values.size() < 2) {
                    if (getMode().equals(ExecutionMode.TOOL)) throw new UnspecifiedRoleException();
                    Command cmd = create("help");
                    cmd.parameter("anonymous").setValues(asList("export"));
                    cmd.run();
                    return;
                }
                role = values.get(1);
                role = convert(role);
                if (role == null) {
                    throw new UnspecifiedRoleException();
                }
                break;
            case "user":
                if (values.size() < 2) {
                    if (getMode().equals(ExecutionMode.TOOL)) exit(1);
                    Command cmd = create("help");
                    cmd.parameter("anonymous").setValues(asList("export"));
                    cmd.run();
                    return;
                }
                user = values.get(1);
                user = convert(user);
                if (user == null) {
                    throw new UnspecifiedUserNameException();
                }
                break;
        }

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        InputStream entity;

        if (getMode().equals(SHELL)) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    print();
                }
            });
            try {
                ExportTaskAdapter task = session.exportService().newTask();
                setExportOptions(task, user, role, /* <path> */ path);
                StateDto state = task.parameters(interParams).create().getEntity();
                t.start();
                entity = session.exportService().task(state.getId()).fetch().getEntity();
                out.printf("\rExport status: SUCCESS\n");
            } catch (Exception e) {
                if (!(e instanceof AuthenticationFailedException)) {
                    out.printf("\rExport status: FAIL\n");
                    return;
                } else {
                    throw new SessionIsNotAvailableException();
                }
            } finally {
                t.stop();
            }
        } else {
            ExportTaskAdapter task = session.exportService().newTask();
            setExportOptions(task, user, role, path);
            StateDto state = task.parameters(interParams).create().getEntity();
            entity = session.exportService().task(state.getId()).fetch().getEntity();
        }

        try {
            String prefix;
            String postfix = "_UTC";

            if (path.equals("all")) {
                prefix = "export-all-";
            } else if (role != null) {
                prefix = "export-role-";
            } else if (user != null) {
                prefix = "export-user-";
            } else {
                prefix = "export-repo-";
            }
            String date = sdf.format(new Date());
            String file = (to == null) ? prefix + date + postfix + ".zip" : to;
            new FileOutputStream(file).write(readFully(entity, -1, false));
            out.printf("\rFile %s was created.\n", file);
        } catch (IOException e) {
            throw new CannotCreateFileException();
        }
    }

    private String convert(String role) {
        String[] tenantPlusRole = role.split("(?:[/]|[|])");
        return tenantPlusRole.length == 2 ? format("%s|%s", tenantPlusRole[1], tenantPlusRole[0]) : role;
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

    @SneakyThrows
    private void print() {
        int counter = 0;
        out.print("\rExporting resources"); // \r ?
        while (true) {
            if (counter == 4) {
                counter = 0;
                out.print("\rExporting resources");
            }
            out.print(".");
            sleep(250);
            counter++;
        }
    }
}