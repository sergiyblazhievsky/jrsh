package com.jaspersoft.cli.tool.api;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Alexander Krasnyanskiy
 */
public interface Configurable<T extends Session> {

    T configure();

    T configureWithCredentials(String username, String password);

    T configure(String serverUrl, String username, String password);
}
