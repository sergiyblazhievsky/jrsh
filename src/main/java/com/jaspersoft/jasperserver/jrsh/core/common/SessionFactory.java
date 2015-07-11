package com.jaspersoft.jasperserver.jrsh.core.common;

import com.jaspersoft.jasperserver.jaxrs.client.core.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jrsh.core.common.config.ClientConnectionConfig;
import com.jaspersoft.jasperserver.jrsh.core.common.config.Timeout;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class SessionFactory {

    private static Session SHARED_SESSION;

    public static Session getSharedSession() {
        return SHARED_SESSION;
    }

    public static Session createUnsharedSession(String url, String username, String password, String organization) {
        return createSession(url, username, password, organization);
    }

    public static Session createSharedSession(String url, String username, String password, String organization) {
        SHARED_SESSION = createSession(url, username, password, organization);
        return SHARED_SESSION;
    }

    protected static Session createSession(String url, String username, String password, String organization) {
        username = (organization == null)
                ? username
                : username.concat("|").concat(organization);

        url = (url.startsWith("http"))
                ? url
                : "http://".concat(url);

        Yaml yml = new Yaml();
        InputStream file = SessionFactory.class.getClassLoader().getResourceAsStream("client.yml");
        ClientConnectionConfig config = yml.loadAs(file, ClientConnectionConfig.class);
        Timeout timeout = config.getTimeout();

        SHARED_SESSION = new Session(
                new SessionStorage(
                        new RestClientConfiguration(url),
                        new AuthenticationCredentials(username, password)));

        SHARED_SESSION
                .getStorage()
                .getConfiguration()
                .setConnectionTimeout(timeout.getConnectionTimeout());

        SHARED_SESSION
                .getStorage()
                .getConfiguration()
                .setReadTimeout(timeout.getReadTimeout());

        return SHARED_SESSION;
    }

    public static void updateSharedSession(Session session) {
        SHARED_SESSION = session;
    }
}
