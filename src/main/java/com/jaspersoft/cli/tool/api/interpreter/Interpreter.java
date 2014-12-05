package com.jaspersoft.cli.tool.api.interpreter;

import com.jaspersoft.cli.tool.api.operation.ClientOperation;

/**
 * @author Alexander Krasnyanskiy
 */
public interface Interpreter {

    void interprete (ClientOperation operation);
}
