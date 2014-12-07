package com.jaspersoft.cli.tool.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ClientConfigurationSessionFactory implements Configurable<Session> {

    @Override
    public Session configure() {
        RestClientConfiguration configuration = new RestClientConfiguration(ConfigType.YML);
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        return client.authenticate("superuser", "superuser");
    }

    @Override
    public Session configure(String username, String password) {
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
