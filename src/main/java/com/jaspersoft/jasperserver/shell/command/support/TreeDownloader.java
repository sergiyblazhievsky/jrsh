package com.jaspersoft.jasperserver.shell.command.support;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
                    .parameter(ResourceSearchParameter.LIMIT, "5000")
                    .search().getEntity().getResourceLookups();
        } catch (Exception e) {
            // if we doesn't have a session
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

    public List<Pair<String, Boolean>> markedList() {
        List<Pair<String, Boolean>> list = new ArrayList<>();
        for (ClientResourceLookup lup : download()) {
            String uri = lup.getUri();
            String resType = lup.getResourceType();
            list.add(new ImmutablePair<>(uri, "folder".equals(resType)));
        }
        return list;
    }

//    public List<Pair<String, Boolean>> filteredList(Filter filter) {
//        List<Pair<String, Boolean>> list = new ArrayList<>();
//        for (ClientResourceLookup lup : download()) {
//            String uri = lup.getUri();
//            String resType = lup.getResourceType();
//            if (resType.equals(filter.toString().toLowerCase())) {
//                list.add(new ImmutablePair<>(uri, filter.toString().toLowerCase().equals(resType)));
//            }
//        }
//        return list;
//    }
//
//    public enum Filter {
//        FOLDER, ALL
//    }
}

