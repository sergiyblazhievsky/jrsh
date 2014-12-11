package com.jaspersoft.cli.tool.core;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.FOLDER_URI;
import static java.lang.Thread.sleep;

public class ServiceOperation {
    protected Session session;

    public ServiceOperation() {
    }

    public ServiceOperation(Session session) {
        this.session = session;
    }

    public void importResource(File resource) {
        StateDto state = session.importService().newTask().create(resource).entity();
        try {
            waitForUpload(state);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void importResource(InputStream resource) {
        StateDto state = session.importService().newTask().create(resource).entity();
        try {
            waitForUpload(state);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> resourceAsList() {
        List<String> converted = new ArrayList<>();
        List<ClientResourceLookup> lookup = session.resourcesService()
                .resources().search()
                .entity()
                .getResourceLookups();
        for (ClientResourceLookup lkp : lookup) {
            converted.add(lkp.getUri());
        }
        return converted;
    }

    public List<String> resourceAsList(Map<String, String> options){
        List<String> converted = new ArrayList<>();
        List<ClientResourceLookup> lookup = session.resourcesService()
                .resources().parameter(FOLDER_URI, options.get("pf"))
                .search().entity()
                .getResourceLookups();
        for (ClientResourceLookup lkp : lookup) {
            converted.add(lkp.getUri());
        }
        return converted;
    }

    private void waitForUpload(StateDto state) throws InterruptedException {
        String currentPhase;
        do {
            currentPhase = getPhaseForState(state);
            if (currentPhase.equals("finished")) {
                break;
            } else {
                sleep(500);
            }
        } while (true);
    }

    private String getPhaseForState(StateDto state) {
        if (state != null) {
            return session.exportService().task(state.getId()).state().entity().getPhase();
        }
        throw new RuntimeException("State cannot be null.");
    }
}
