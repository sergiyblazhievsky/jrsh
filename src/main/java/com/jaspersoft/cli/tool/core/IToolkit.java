package com.jaspersoft.cli.tool.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

import java.util.Map;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface IToolkit {
    void process();
    void help();
    void importData(Map<String, String> options);
    void profile();
    Session connect(String url, String username, String password);
    void version();
    void readCommand();
    void tree(Map<String, String> options);
}
