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
import jline.console.ConsoleReader;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.factory.SessionFactory.createImmutable;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReplicateCommand extends Command implements ConsoleReaderAware {

    private static final String FILE = "/Users/alexkrasnyaskiy/IdeaProjects/jasperserver-shell/src/main/resources/jrsh-profile.yml";
    private ConsoleReader reader;

    public ReplicateCommand() {
        name = "replicate";
        description = "Replicate JRS configuration from one JRS to another."
                /* + "\n\t\t\t\t Please notice that you should load your profile configuration first."*/;
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
        if (config == null) {
            try {
                config = ProfileConfigurationFactory.create(FILE);

                if (parameter("anonymous").getValues().size() != 2) {
                    throw new MandatoryParameterMissingException();
                }

                String from = parameter("anonymous").getValues().get(0);
                String to = parameter("anonymous").getValues().get(1);

                Profile src = ProfileUtil.find(config, from);
                Profile dest = ProfileUtil.find(config, to);

                if (src == null || dest == null) {
                    throw new WrongProfileNameException();
                }
                PasswordTokenizer tokenizer = askPasswords(from, to);
                Session exp = createImmutable(src.getUrl(), src.getUsername(), tokenizer.nextToken(), src.getOrganization());
                Session imp = createImmutable(dest.getUrl(), dest.getUsername(), tokenizer.nextToken(), dest.getOrganization());
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
                throw new UnknownParserException(); // fixme <-
            } finally {
                reader.setPrompt("\u001B[1m>>> \u001B[0m");
            }
        } else {
            try {
                if (parameter("anonymous").getValues().size() != 2) {
                    throw new MandatoryParameterMissingException();
                }

                String from = parameter("anonymous").getValues().get(0);
                String to = parameter("anonymous").getValues().get(1);

                Profile src = ProfileUtil.find(config, from);
                Profile dest = ProfileUtil.find(config, to);

                if (src == null || dest == null) {
                    throw new WrongProfileNameException();
                }
                PasswordTokenizer tokenizer = askPasswords(from, to);
                //System.out.println("\rSessions establishing...");
                Session exp = createImmutable(src.getUrl(), src.getUsername(), tokenizer.nextToken(), src.getOrganization());
                Session imp = createImmutable(dest.getUrl(), dest.getUsername(), tokenizer.nextToken(), dest.getOrganization());
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
                out.printf("\rReplication status: FAIL\n");
                throw new UnknownParserException();
            } finally {
                reader.setPrompt("\u001B[1m>>> \u001B[0m");
            }
        }
    }

    private PasswordTokenizer askPasswords(String src, String dest) throws IOException {
        String passSrc = reader.readLine("Please enter the password for " + src + " JRS: ");
        String passDest = reader.readLine("Please enter the password for " + dest + " JRS: ");
        if (passSrc.equals("") || passDest.equals("")) {
            throw new WrongPasswordException();
        }
        return new PasswordTokenizer(passSrc, passDest);
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

    @Override
    public void setReader(ConsoleReader reader) {
        this.reader = reader;
    }

    private class PasswordTokenizer {
        private List<String> tokens;
        private Iterator<String> it;

        public PasswordTokenizer(String first, String second) {
            tokens = new ArrayList<>(asList(first, second));
            it = tokens.iterator();
        }

        protected String nextToken() {
            return it.next();
        }
    }
}
