package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.shell.command.exp.RepositoryDataExporter;
import com.jaspersoft.jasperserver.shell.command.imp.RepositoryDataImporter;
import com.jaspersoft.jasperserver.shell.exception.MandatoryParameterMissingException;
import com.jaspersoft.jasperserver.shell.exception.WrongPasswordException;
import com.jaspersoft.jasperserver.shell.exception.parser.UnknownParserException;
import com.jaspersoft.jasperserver.shell.exception.profile.CannotLoadProfileConfiguration;
import com.jaspersoft.jasperserver.shell.exception.profile.WrongProfileNameException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.Profile;
import com.jaspersoft.jasperserver.shell.profile.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.ProfileConfigurationFactory;
import com.jaspersoft.jasperserver.shell.profile.ProfileUtil;
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

    private static final String FILE = "../config/profile.yml";

    public ReplicateCommand() {
        name = "replicate";
        description = "Replicate JRS configuration from one JRS to another.";
        // Please notice that you should load your profile configuration first
        usageDescription = "\tUsage: replicate <src-profile-name> to <dest-profile-name>";
        parameters.add(new Parameter().setName("anonymous").setMultiple(true));
        parameters.add(new Parameter().setName("to")/* bug in parser! */.setOptional(true));
    }

    @Override
    void run() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                print();
            }
        });

        ProfileConfiguration config = ProfileConfigurationFactory.get();
        try {
            if (config == null) {
                config = ProfileConfigurationFactory.create(FILE);
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

            t.start();

            RepositoryDataExporter exporter = new RepositoryDataExporter(exp);
            InputStream data = exporter.export();
            RepositoryDataImporter importer = new RepositoryDataImporter(imp);
            importer.importData(data);

            t.stop();

            out.printf("\rReplication status: SUCCESS\n");
        } catch (FileNotFoundException e) {
            t.stop();
            out.printf("\rReplication status: FAIL\n");
            throw new CannotLoadProfileConfiguration();
        } catch (IOException e) {
            t.stop();
            throw new UnknownParserException(); // fixme :: wtf?
        } finally {
            reader.setPrompt("\u001B[1m>>> \u001B[0m");
        }
    }

    private String askPasswords(String jrsName) throws IOException {
        String pass = reader.readLine("Please enter the password for " + jrsName + " JRS: ", '*');
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
