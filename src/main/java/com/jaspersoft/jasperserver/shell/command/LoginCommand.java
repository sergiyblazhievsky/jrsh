package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.exception.MandatoryParameterMissingException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;

import java.util.List;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.create;

/**
 * @author Alexander Krasnyanskiy
 */
public class LoginCommand extends Command {

    public LoginCommand() {
        name = "login";
        description = "Login to JasperReportsServer.";
        parameters.add(new Parameter().setName("server").setKey("--server"));
        parameters.add(new Parameter().setName("username").setKey("--username"));
        parameters.add(new Parameter().setName("password").setKey("--password"));
        parameters.add(new Parameter().setName("organization").setKey("--organization").setOptional(true));
    }

    @Override
    void run() {

        List<String> serverParamValues = parameter("server").getValues();
        List<String> usernameParamValues = parameter("username").getValues();
        List<String> passwordParamValues = parameter("password").getValues();
        List<String> organizationParamValues = parameter("organization").getValues();

        String url;
        String username;
        String password;
        String organization = null;

        if (!serverParamValues.isEmpty() && !usernameParamValues.isEmpty() && !passwordParamValues.isEmpty()) {
            url = parameter("server").getValues().get(0);
            username = parameter("username").getValues().get(0);
            password = parameter("password").getValues().get(0);
            if (!organizationParamValues.isEmpty()) {
                organization = parameter("organization").getValues().get(0);
            }
        } else {
            throw new MandatoryParameterMissingException();
        }

        create(url, username, password, organization);

        profile.setUrl(url);
        profile.setUsername(username);
        profile.setOrganization(organization);
    }
}
