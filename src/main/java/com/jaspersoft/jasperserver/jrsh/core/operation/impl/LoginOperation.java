package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.WrongConnectionStringFormatException;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import lombok.Data;

import static java.lang.String.format;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Master(name = "login", description = "This is a login operation")
public class LoginOperation implements Operation {

    public static int counter = 0;

    private String server;
    private String username;
    private String password;
    private String organization;

    @Parameter(mandatory = true, dependsOn = "login", values =
    @Value(tokenAlias = "CS", tail = true))
    private String connectionString;

    @Override
    public OperationResult eval(Session ignored) {
        OperationResult result;
        try {
            SessionFactory.createSharedSession(server, username, password, organization);
            result = new OperationResult(
                    format("You have logged in as [%s]", username),
                    ResultCode.SUCCESS, this, null);
            //
            // Counting only successful attempts
            //
            counter++;
        } catch (Exception err) {
            result = new OperationResult(
                    format("Login failed [%s]", err.getMessage()),
                    ResultCode.FAILED, this, null);
        }
        return result;
    }

    /**
     * Parses connection string and sets the fields
     *
     * @param connectionString connection string
     */
    public void setConnectionString(String connectionString) {
        if (!TokenPreconditions.isConnectionString(connectionString)) {
            throw new WrongConnectionStringFormatException();
        }
        this.connectionString = connectionString;
        String[] tokens = connectionString.split("[@]");
        switch (tokens.length) {
            case 2:
                server = tokens[1].trim();
                tokens = tokens[0].split("[%]");
                switch (tokens.length) {
                    case 2:
                        password = tokens[1].trim();
                        tokens = tokens[0].split("[|]");
                        switch (tokens.length) {
                            case 2:
                                username = tokens[0].trim();
                                organization = tokens[1].trim();
                                break;
                            case 1:
                                username = tokens[0].trim();
                                break;
                        }
                        break;
                    case 1:
                        tokens = tokens[0].split("[|]");
                        switch (tokens.length) {
                            case 2:
                                username = tokens[0].trim();
                                organization = tokens[1].trim();
                                break;
                            default:
                                username = tokens[0].trim();
                                break;
                        }
                        break;
                }
                break;
        }
    }

    public String getConnectionString() {
        return connectionString;
    }
}
