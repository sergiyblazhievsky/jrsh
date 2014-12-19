package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.io.File;

import static java.lang.Thread.sleep;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "import")
public class ImportCommand extends AbstractCommand<Void> {

    @Parameter(names = {"--zip", "-z"}, required = true)
    public String file;

    public ImportCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        StateDto state = SessionFactory.getInstance().importService().newTask().create(new File(file)).entity();
        waitForUpload(state);
        return null;
    }

    /**
     * Waits until job has been executed.
     *
     * @param state state of the job
     */
    @SneakyThrows
    private void waitForUpload(StateDto state) {
        do {
            if ("finished".equals(getPhase(state))) break;
            sleep(500);
        } while (true);
    }

    /**
     * Retrieves current phase for the job.
     *
     * @param state state of the job
     * @return phase
     */
    private String getPhase(StateDto state) {
        return SessionFactory.getInstance().exportService().task(state.getId()).state().entity().getPhase();
    }
}