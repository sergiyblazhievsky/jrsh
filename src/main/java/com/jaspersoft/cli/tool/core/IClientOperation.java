package com.jaspersoft.cli.tool.core;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportParameter;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface IClientOperation {
    ClientResource getResource(String uri);
    void importResource(InputStream resource);
    void importResource(InputStream resource, Map<ImportParameter, Boolean> parameters);
    void importResource(File resource);
    List<String> resourceAsList();
    List<String> resourceAsList(Map<String, String> options);
}
