package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * Base interface for every operation in JRSH.
 *
 * @author Alex Krasnyanskiy
 * @version 1.0
 */
public interface Operation {
    /**
     * Evaluates an operation using Rest client session (if needed).
     * Any operation can interact with JasperReport Server
     * and able to return the result of evaluation.
     *
     * @param session session
     * @return result
     */
    OperationResult eval(Session session);

}