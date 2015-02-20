package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.common.RepositoryDataExporter;
import com.jaspersoft.jasperserver.shell.command.common.RepositoryDataImporter;
import com.jaspersoft.jasperserver.shell.exception.MandatoryParameterMissingException;
import com.jaspersoft.jasperserver.shell.exception.WrongPasswordException;
import com.jaspersoft.jasperserver.shell.exception.parser.UnknownParserException;
import com.jaspersoft.jasperserver.shell.exception.profile.CannotFindProfileConfigurationException;
import com.jaspersoft.jasperserver.shell.exception.profile.CannotLoadProfileConfiguration;
import com.jaspersoft.jasperserver.shell.exception.profile.WrongProfileNameException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.util.ProfileUtil;
import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.createImmutable;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReplicateCommand extends Command {

    public ReplicateCommand() {
        name = "replicate";
        description = "Replicate JRS configuration from one JRS to another.";
        usageDescription = "\tUsage: replicate <src-profile-name> to <dest-profile-name>";
        parameters.add(new Parameter().setName("anonymous").setMultiple(true));
        parameters.add(new Parameter().setName("to")/* todo bug in parser! */.setOptional(true));
    }

    @Override
    public void run() {
        Thread spinner = new Thread(new Runnable() {
            public void run() {print();}
        });
        spinner.setDaemon(true);
        ProfileConfiguration config = ProfileConfigurationFactory.getConfiguration();
        try {
            if (config == null) {
                throw new CannotFindProfileConfigurationException();
            }
            if (parameter("anonymous").getValues().size() != 2 || !parameter("to").isAvailable()) {
                throw new MandatoryParameterMissingException();
            }
            String from = parameter("anonymous").getValues().get(0);
            String to = parameter("anonymous").getValues().get(1);
            Profile src = ProfileUtil.find(config, from);
            Profile dest = ProfileUtil.find(config, to);
            if (src == null || dest == null) {
                throw new WrongProfileNameException();
            }
            Session exp = createImmutable(src.getUrl(), src.getUsername(), askPasswords(from), src.getOrganization());
            Session imp = createImmutable(dest.getUrl(), dest.getUsername(), askPasswords(to), dest.getOrganization());
            spinner.start();
            RepositoryDataExporter exporter = new RepositoryDataExporter(exp);
            InputStream data = exporter.export();
            RepositoryDataImporter importer = new RepositoryDataImporter(imp);
            importer.importData(data);
            spinner.stop();
            exp.logout();
            imp.logout();
            out.printf("\rReplication status: SUCCESS\n");
        } catch (FileNotFoundException e) {
            spinner.stop();
            out.printf("\rReplication status: FAIL\n");
            throw new CannotLoadProfileConfiguration();
        } catch (IOException e) {
            spinner.stop();
            throw new UnknownParserException(); // fixme!
        } finally {
            reader.setPrompt("\u001B[1m>>> \u001B[0m");
        }
    }

    private String askPasswords(String jrsName) throws IOException {
        String username = "from whom?";
        for (Profile p : ProfileConfigurationFactory.getConfiguration().getProfiles()) {
            if (jrsName.equals(p.getName())){
                username = p.getUsername();
            }
        }
        String pass = reader.readLine("Please enter the password for <" + username + "> at <" + jrsName + "> environment: ", '*');
        if (pass.equals("")) {
            throw new WrongPasswordException();
        }
        return pass;
    }

    @SneakyThrows
    private void print() {
        int counter = 0;
        out.print("\rReplication");
        while (true) {
            if (counter == 4) {
                counter = 0;
                out.print("\rReplication");
            }
            out.print(".");
            sleep(250);
            counter++;
        }
    }
}
