package com.jaspersoft.cli.tool.core;

import com.jaspersoft.cli.tool.api.Configurable;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;

/**
 * @author Alexander Krasnyanskiy
 */
public class ClientConfigurationSessionFactory implements Configurable<Session> {

    /**
     * Default credentials for JRS
     */
    private static final String USERNAME = "superuser";
    private static final String PASSWORD = "superuser";

    @Override
    public Session configure() {
        RestClientConfiguration configuration = new RestClientConfiguration(ConfigType.YML);
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        return client.authenticate(USERNAME, PASSWORD);
    }

    @Override
    public Session configureWithCredentials(String username, String password) {
        RestClientConfiguration configuration = new RestClientConfiguration(ConfigType.YML);
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        return client.authenticate(username, password);
    }

    @Override
    public Session configure(String serverUrl, String username, String password) {
        RestClientConfiguration configuration = new RestClientConfiguration(serverUrl);
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        return client.authenticate(username, password);
    }
}
