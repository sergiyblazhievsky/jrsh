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
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.EVERYTHING;
import static com.jaspersoft.jasperserver.shell.ExecutionMode.SHELL;
import static com.jaspersoft.jasperserver.shell.ExecutionMode.TOOL;
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
    private Session session;
    private InputStream entity;

    public ExportCommand() {
        name = "export";
        description = "Exports configuration of JasperReportsServer.";
        parameters.add(new Parameter().setName("anonymous")/*.setOptional(true)*/.setMultiple(true)); // <path>

        parameters.add(new Parameter().setName("without-access-events").setOptional(true));
        parameters.add(new Parameter().setName("without-audit-events").setOptional(true));
        parameters.add(new Parameter().setName("without-monitoring-events").setOptional(true));

        parameters.add(new Parameter().setName("without-events").setOptional(true));
        parameters.add(new Parameter().setName("without-users-and-roles").setOptional(true));

    }

    @Override
    void run() {

        session = getInstance();

        String pathOrSubCommand;
        String role = null;
        String user = null;

        List<String> values = parameter("anonymous").getValues();

        // if params haven't specified then show info for export command
        if (!values.isEmpty()) {
            pathOrSubCommand = values.get(0);
        } else {
            Command cmd = create("help");
            cmd.parameter("anonymous").setValues(asList("export"));
            cmd.run();
            return;
        }


        // todo :: переписать
        //         => возможны мультипараметры
        switch (pathOrSubCommand) {
            case "all":
                interParams.add(EVERYTHING);
                break;
            case "role":


                // >>> export role | without role name => error!


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


                // >>> export user | without username => error!


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
                    //if (getMode().equals(ExecutionMode.TOOL)) exit(1);
                    throw new UnspecifiedUserNameException();
                }
                break;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String date = sdf.format(new Date());

        if (getMode().equals(SHELL)) {
            Thread t = new Thread(this::print);

            try {
                ExportTaskAdapter task = session.exportService().newTask();
                setExportOptions(task, user, role, pathOrSubCommand /* <path> */);
                StateDto state = task.parameters(interParams).create().entity();
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
        } else if (getMode().equals(TOOL)) { // => [ToolMode]

            // without any message (silent mode)

            ExportTaskAdapter task = session.exportService().newTask();
            setExportOptions(task, user, role, pathOrSubCommand);
            StateDto state = task.parameters(interParams).create().entity();
            entity = session.exportService().task(state.getId()).fetch().getEntity();
        }

        try {
            String postfix;
            if (pathOrSubCommand.equals("all")) postfix = "all-";
            else if (role != null) postfix = "role-";
            else if (user != null) postfix = "user-";
            else postfix = "repo-";

            new FileOutputStream("/Users/alexkrasnyaskiy/IdeaProjects/jrsh/src/main/resources/export-"
                    + postfix + date + ".zip").write(readFully(entity, -1, false));
        } catch (IOException e) {
            //if (getMode().equals(ExecutionMode.TOOL)) exit(1);
            throw new CannotCreateFileException();
        }


    }

    private String convert(String role) {
        String[] tenantRole = role.split("(?:[/]|[|])");
        if (tenantRole.length == 2) {
            return format("%s|%s", tenantRole[1], tenantRole[0]);
        }
        return role;
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
        out.print("Exporting resources");
        while (true) {
            if (counter == 5) {
                counter = 0;
                out.print("\rExporting resources");
            }
            out.print(".");
            sleep(250);
            counter++;
        }
    }
}
