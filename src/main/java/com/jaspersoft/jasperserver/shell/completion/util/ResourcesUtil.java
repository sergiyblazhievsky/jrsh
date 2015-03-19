package com.jaspersoft.jasperserver.shell.completion.util;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexander Krasnyanskiy
 */
public final class ResourcesUtil {

    private ResourcesUtil() {
        // Don't use it.
    }

    /**
     * Separates the resource name from the URL. If the resource is a folder
     * then add slash mark to the end of the name.
     * <p/>
     * Example: /folder => folder/
     *
     * @param resources processed resources
     */
    public static List<String> process(Map<String, Boolean> resources) {
        List<String> processedResources = newArrayList();
        for (Map.Entry<String, Boolean> entry : resources.entrySet()) {
            String url = entry.getKey();
            if (entry.getValue()) { // is Folder
                String resourceName = url.substring(url.lastIndexOf("/"));
                if (resourceName.startsWith("/")) {
                    resourceName = resourceName.substring(1).concat("/");
                    processedResources.add(resourceName);
                }
            } else {
                String resourceName = url.substring(url.lastIndexOf("/"));
                resourceName = resourceName.substring(1);
                processedResources.add(resourceName);
            }
        }
        return processedResources;
    }

    /**
     * Searches strings which start with source.
     * <p/>
     * Example: ex => example/ (root: ex)
     *
     * @param src        source
     * @param collection content
     * @return matched elements
     */
    public static List<String> filter(String src, Collection<String> collection) {
        List<String> matched = newArrayList();
        for (String elem : collection) {
            if (elem.startsWith(src)) {
                matched.add(elem);
            }
        }
        return matched;
    }


    /**
     * Removes last stash in string.
     * <p/>
     * Example: resource/ => resource
     *
     * @param path path
     * @return new path
     */
    public static String normalize(String path) {
        if (path.endsWith("/") && path.length() > 1) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * Extracts pure resource names.
     * <p/>
     * Example: /resource/ => resource
     *
     * @param buffer input
     * @return pure resource name
     */
    public static String extract(String buffer) {

        // Input validation
        Preconditions.checkNotNull(buffer, "Buffer cannot be null.");
        Preconditions.checkState(!"".equals(buffer), "Buffer cannot be empty.");

        return buffer.substring(buffer.lastIndexOf("/") + 1);
    }

}
