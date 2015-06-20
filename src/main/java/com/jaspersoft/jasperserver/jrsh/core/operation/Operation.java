package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * Base interface for all JRSH operations.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public interface Operation {

    /**
     * Executes an operation business logic that
     * uses a session in case if it interacts
     * with JRS.
     *
     * @param session REST client session
     * @return result
     */
    OperationResult execute(Session session);

}