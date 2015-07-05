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
     * Executes an operation business
     * logic.
     *
     * @param session REST client session
     *                (could be `null` if operation isn't interactive)
     * @return operation result
     */
    OperationResult execute(Session session);

}