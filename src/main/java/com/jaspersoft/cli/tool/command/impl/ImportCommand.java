package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.File;

import static java.lang.Thread.sleep;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "import")
public class ImportCommand extends AbstractCommand<Void> {

    public ImportCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Parameter(names = {"--zip", "-z"}, required = true)
    public String file;

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
    private void waitForUpload(StateDto state) {
        String currentPhase;
        do {
            currentPhase = getPhase(state);
            if (currentPhase.equals("finished")) {
                break;
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    /**
     * Retrieves current phase for the job.
     *
     * @param state state of the job
     * @return phase
     */
    private String getPhase(StateDto state) {
        if (state != null) {
            return SessionFactory.getInstance().exportService().task(state.getId()).state().entity().getPhase();
        }
        throw new RuntimeException("State cannot be null.");
    }
}