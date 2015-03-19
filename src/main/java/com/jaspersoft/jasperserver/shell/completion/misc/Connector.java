package com.jaspersoft.jasperserver.shell.completion.misc;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;

/**
 * Connector.
 */
//@Log4j
public class Connector {

    //private Session session;
    //private Properties props;
    //private static final String PATH = "/server.properties";

    /**
     * Constructor.
     */
    //@SneakyThrows
    public Connector() {
//        props = new Properties();
//        InputStream stream = Connector.class.getClass().getResourceAsStream(PATH);
//        props.load(stream);
    }

    /**
     * Connects to JRS.
     * @return session
     */
    public Session connect() {

        /**
         * Create new a session if it is not available, otherwise return an existing session
         */
//        if (session == null) {
//            log.info("Start connection...");
//            RestClientConfiguration configuration = new RestClientConfiguration(props.getProperty("jrs.server.url"));
//            JasperserverRestClient client = new JasperserverRestClient(configuration);
//            Session session = client.authenticate(props.getProperty("jrs.server.username"), props.getProperty("jrs.server.password"));
//            log.info("Established.");
//            return this.session = session;
//        } else {
//            return session;
//        }

        return SessionFactory.getInstance();

    }
}
