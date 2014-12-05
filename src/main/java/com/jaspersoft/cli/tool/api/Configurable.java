package com.jaspersoft.cli.tool.api;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * Base interface for JRS Rest Client configuration.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Configurable<T extends Session> {


    /**
     * Configure environment with default credentials and read server
     * configuration context from a YML file.
     * @return created Session object
     */
    T configure();


    /**
     * Configure environment with provided credentials and read server
     * configuration context from a YML file.
     * @return created Session object
     */
    T configure(String username, String password);


    /**
     * Configure environment with provided credentials and server url.
     * @param serverUrl given JRS URL
     * @param username username form JRS authentication
     * @param password password form JRS authentication
     * @return created Session object
     */
    T configure(String serverUrl, String username, String password);
}
