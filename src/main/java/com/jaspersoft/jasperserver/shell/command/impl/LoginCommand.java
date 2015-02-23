package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.ExecutionMode;
import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.common.TreeDownloader;
import com.jaspersoft.jasperserver.shell.completion.completer.RepositoryPathCompleter;
import com.jaspersoft.jasperserver.shell.exception.MandatoryParameterMissingException;
import com.jaspersoft.jasperserver.shell.exception.WrongPasswordException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.entity.Profile;

import java.io.IOException;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.createSession;
import static com.jaspersoft.jasperserver.shell.profile.ProfileUtil.isEmpty;
import static com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory.getConfiguration;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class LoginCommand extends Command {

    public LoginCommand() {
        name = "login";
        description = "Login to JasperReportsServer.";
        usageDescription = "\tUsage: login --server <url> --username <name> --password <pwd> --organization <org>";
        parameters.add(new Parameter().setName("server").setKey("--server"));
        parameters.add(new Parameter().setName("username").setKey("--username"));
        parameters.add(new Parameter().setName("password").setKey("--password"));
        parameters.add(new Parameter().setName("organization").setKey("--organization").setOptional(true));
    }

    @Override
    public void run() {

        List<String> serverParamValues = parameter("server").getValues();
        List<String> usernameParamValues = parameter("username").getValues();
        List<String> passwordParamValues = parameter("password").getValues();
        List<String> organizationParamValues = parameter("organization").getValues();

        String url;
        String username;
        String password = null;
        String organization = null;
        String profileName = "Current";

        if (!serverParamValues.isEmpty() && !usernameParamValues.isEmpty() && !passwordParamValues.isEmpty()) {
            url = parameter("server").getValues().get(0);
            username = parameter("username").getValues().get(0);
            password = parameter("password").getValues().get(0);
            if (!organizationParamValues.isEmpty()) {
                organization = parameter("organization").getValues().get(0);
            }
        } else {
            // if default profile is already loaded, then we use it for login operation
            if (!isEmpty(profile)) {
                url = profile.getUrl();
                username = profile.getUsername();
                profileName = profile.getName();
                try {
                    password = askPasswords(profile.getName());
                } catch (IOException ignored) {}
            } else {
                throw new MandatoryParameterMissingException();
            }
        }


        createSession(url, username, password, organization);


        if (isEmpty(profile)) {
            profile.setName(profileName);
            profile.setUrl(url);
            profile.setUsername(username);
            profile.setOrganization(organization);
        } else {
            profile.setName(profileName);
        }


        /*
         * Hack! todo: Delete it!
         */
        if (RepositoryPathCompleter.resources == null || RepositoryPathCompleter.resources.isEmpty()) {
            RepositoryPathCompleter.resources = new TreeDownloader().markedList();
        }
//        if (FolderRepositoryPathCompleter.resources == null || FolderRepositoryPathCompleter.resources.isEmpty()) {
//            FolderRepositoryPathCompleter.resources = new TreeDownloader().filteredList(Filter.FOLDER);
//        }


        if (getMode().equals(ExecutionMode.SHELL)) {
            out.println("You've logged in.");
        }
    }

    private String askPasswords(String jrsName) throws IOException {
        String username = "from whom?";
        for (Profile profile : getConfiguration().getProfiles()) {
            if (jrsName.equals(profile.getName())) {
                username = profile.getUsername();
            }
        }
        String pass = reader.readLine("Please enter the password for <" + username + "> at <" + jrsName + "> environment: ", '*');
        if (pass.equals("")) {
            throw new WrongPasswordException();
        }
        reader.setPrompt("\u001B[1m>>> \u001B[0m");
        return pass;
    }
}
