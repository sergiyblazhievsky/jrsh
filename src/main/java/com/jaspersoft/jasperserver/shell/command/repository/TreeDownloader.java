package com.jaspersoft.jasperserver.shell.command.repository;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
@Deprecated
public class TreeDownloader {

    public List<ClientResourceLookup> download() {
        List<ClientResourceLookup> lookups;
        try {
            lookups = SessionFactory.getInstance().resourcesService().resources()
                    .parameter(ResourceSearchParameter.FOLDER_URI, "/")
                    .parameter(ResourceSearchParameter.LIMIT, "10000")
                    .search().getEntity().getResourceLookups();
        } catch (Exception e) {
            // if we doesn't have a session yet
            lookups = new ArrayList<>();
        }
        return lookups;
    }

    public List<String> list() {
        List<String> list = new ArrayList<>();
        for (ClientResourceLookup lup : download()) {
            list.add(lup.getUri());
        }
        return list;
    }
}

