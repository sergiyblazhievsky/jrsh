package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import lombok.Data;

import java.io.File;

import static java.lang.Thread.sleep;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Parameters(commandDescription = "import")
public class ImportCommand extends AbstractCommand<Void> {

    @Parameter(names = {"--file", "-f"}, required = true)
    public String file;

    //
    // fixme: recursion?!
    //
    @Override
    public Void execute() {
        File zip = new File(file);
        StateDto state = jrsRestClientSession.importService().newTask().create(zip).entity();
        try {
            waitForUpload(state);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Waits until job has been executed.
     *
     * @param state state of the job
     * @throws InterruptedException
     */
    private void waitForUpload(StateDto state) throws InterruptedException {
        String currentPhase;
        do {
            currentPhase = getPhase(state);
            if (currentPhase.equals("finished")) {
                break;
            }
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
        if (state != null) {
            return jrsRestClientSession.exportService().task(state.getId()).state().entity().getPhase();
        }
        throw new RuntimeException("State cannot be null.");
    }
}