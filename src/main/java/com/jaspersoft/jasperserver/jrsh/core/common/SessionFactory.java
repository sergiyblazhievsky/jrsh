package com.jaspersoft.jasperserver.jrsh.core.common;

import com.jaspersoft.jasperserver.jaxrs.client.core.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * @author Alex Krasnyanskiy
 */
public class SessionFactory {

    private static Session SHARED_SESSION;

    public static Session getSharedSession() {
        return SHARED_SESSION;
    }

    public static Session createUnsharedSession(String url, String username, String password, String organization) {
        return createSession(url, username, password, organization);
    }

    public static Session createSharedSession(String url, String username, String password, String organization) {
        return SHARED_SESSION = createSession(url, username, password, organization);
    }

    private static Session createSession(String url, String username, String password, String organization) {
        username = organization == null ? username : username.concat("|").concat(organization);
        url = url.startsWith("http") ? url : "http://".concat(url);
        return new Session(new SessionStorage(new RestClientConfiguration(url), new AuthenticationCredentials(username, password)));
    }
}
