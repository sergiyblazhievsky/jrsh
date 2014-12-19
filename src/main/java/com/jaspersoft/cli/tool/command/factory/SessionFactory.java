package com.jaspersoft.cli.tool.command.factory;

import com.jaspersoft.cli.tool.exception.MissingConnectionInformationException;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Alex Krasnyanskiy
 */
public class SessionFactory {

    private static Session session;

    private SessionFactory() {
    }

    public static Session create(String url, String username, String password) {
        RestClientConfiguration configuration = new RestClientConfiguration(url);
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        session = client.authenticate(username, password);
        return session;
    }

    public static Session getInstance() {
        if (session == null) {
            throw new MissingConnectionInformationException();
        } else {
            return session;
        }
    }
}
