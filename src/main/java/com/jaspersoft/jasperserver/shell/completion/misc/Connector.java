package com.jaspersoft.jasperserver.shell.completion.misc;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

import static com.jaspersoft.jasperserver.shell.SessionFactory.getInstance;

/**
 * @author Alexander Krasnyanskiy
 */
public class Connector {
    public Session connect() {
        return getInstance();
    }
}
