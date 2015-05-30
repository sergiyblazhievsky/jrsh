package com.jaspersoft.jasperserver.jrsh.core.completion.impl;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import jline.console.completer.Completer;

import java.util.*;

@Deprecated
public class RepositoryStaticCompleter implements Completer {

    public static List<String> resources;

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        if (buffer == null) {
            buffer = "";
        }
        String translated = buffer;

        if (resources == null) {
            resources = new RepositoryContentReceiver().receive();
        }

        return matchFiles(translated, resources, candidates);
    }

    protected int matchFiles(String translated, List<String> resources, List<CharSequence> candidates) {
        if (resources == null) {
            return -1;
        }
        for (String resource : resources) {
            if (resource.startsWith(translated)) {
                String name = resource + " ";
                candidates.add(name);
            }
        }

        String common = commonSubstring(translated, resources);
        String diff = diff(translated, common);
        if (!diff.isEmpty()) {
            candidates.clear();
            candidates.add(diff);
            return translated.length();
        }

        Set<String> cuts = new HashSet<>();

        for (String r : resources) {
            if (r.startsWith(common)) {
                String s = r.substring(common.length());
                int idx = s.indexOf("/");
                String cut = idx < 0 ? s : s.substring(0, idx);
                cuts.add(cut + (idx < 0 ? "" : "/"));
            }
        }

        if (!cuts.isEmpty()) {
            candidates.clear();
            if (cuts.contains("/")) {
                candidates.add("/");
            } else {
                candidates.addAll(cuts);
            }
        }

        return translated.length();
    }

    private String commonSubstring(String src, Collection<String> col) {
        Set<String> set = new TreeSet<>();
        for (String el : col) {
            if (el.startsWith(src)) set.add(el);
        }
        String common = "", small = "", temp = "";
        for (String el : set.toArray(new String[set.size()])) {
            if (small.length() < el.length()) small = el;
        }
        char[] smallStrChars = small.toCharArray();
        for (char c : smallStrChars) {
            temp += c;
            for (String el : set.toArray(new String[set.size()])) {
                if (!el.contains(temp)) {
                    temp = "";
                    break;
                }
            }
            if (!temp.equals("") && temp.length() > common.length()) {
                common = temp;
            }
        }
        return common;
    }

    private String diff(String input, String common) {
        return common.isEmpty() ? "" : common.substring(input.length());
    }

    private class RepositoryContentReceiver {
        public List<String> receive() {
            List<String> list = new ArrayList<>();
            List<ClientResourceLookup> lookups =
                    SessionFactory.getSharedSession()
                            .resourcesService()
                            .resources()
                            .parameter(ResourceSearchParameter.FOLDER_URI, "/")
                            .parameter(ResourceSearchParameter.LIMIT, "500")
                            .search()
                            .getEntity()
                            .getResourceLookups();
            for (ClientResourceLookup lookup : lookups) {
                list.add(lookup.getUri());
            }
            return list;
        }
    }
}

