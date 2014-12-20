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

    /**
     * Creates client session with the consideration of specified organization.
     * @param url JRS url
     * @param username JRS username
     * @param password JRS password
     * @param organizationName organization name
     * @return Session instance
     */
    public static Session create(String url, String username, String password, String organizationName) {
        RestClientConfiguration configuration = new RestClientConfiguration(url);
        JasperserverRestClient client = new JasperserverRestClient(configuration);

        session = organizationName != null
                ? client.authenticate(username + "|" + organizationName, password)
                : client.authenticate(username, password);
        return session;
    }

    /**
     * Smart getter for class variable.
     * @return instance of the factory
     */
    public static Session getInstance() {
        if (session == null) {
            throw new MissingConnectionInformationException();
        } else {
            return session;
        }
    }
}
