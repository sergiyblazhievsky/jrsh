package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;
import com.jaspersoft.jasperserver.shell.command.repository.TreeConverter;
import com.jaspersoft.jasperserver.shell.command.repository.TreeNode;
import com.jaspersoft.jasperserver.shell.exception.server.JrsResourceNotFoundException;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import lombok.SneakyThrows;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.FOLDER_URI;
import static com.jaspersoft.jasperserver.shell.validator.RepositoryPathValidatorUtil.validate;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

/**
 * @author Alexander Krasnyanskiy
 */
public class ShowCommand extends Command {

    private Session session;

    public ShowCommand() {
        name = "show";
        description = "Show information about JRS or print configuration tree.";
        usageDescription = "\tUsage:  show repo <path>\n" +
                "\t\t\tshow server-info";
        parameters.add(new Parameter().setName("anonymous").setOptional(true));
        parameters.add(new Parameter().setName("server-info").setOptional(true));
        parameters.add(new Parameter().setName("repo").setOptional(true));
    }

    @Override
    void run() {

        session = SessionFactory.getInstance();

        Parameter repoParam = parameter("repo");
        Parameter anonParam = parameter("anonymous");
        Parameter infoParam = parameter("server-info");


        if (repoParam.isAvailable()) {
            if (anonParam.getValues().isEmpty()) {
                printTree("");
            } else {
                printTree(anonParam.getValues().get(0));
            }
        } else if (infoParam.isAvailable()) {
            printInfo();
        } else {
            out.println("See `help show`.");
        }
    }

    private void printInfo() {
        ServerInfo info = session.serverInfoService().details().getEntity();
        out.printf("\nExpiration: \u001B[31m%s\u001B[0m\n"+
                        "\nVersion: %s" +
                        "\nFeatures: %s" +
                        "\nEdition: %s" +
                        "\nLicenseType: %s" +
                        "\nDateFormatPattern: %s" +
                        "\nDatetimeFormatPattern: %s" +
                        "\nBuild: %s\n\n",
                info.getExpiration() == null ? "unknown" : info.getExpiration(),
                info.getVersion(),
                info.getFeatures(),
                info.getEditionName() + " " + info.getEdition(),
                info.getLicenseType(),
                info.getDateFormatPattern(),
                info.getDatetimeFormatPattern(),
                info.getBuild()
        );
    }


    void printTree(String path) {

        Thread spinner = new Thread(new Runnable() {
            @SneakyThrows
            public void run() {
                int counter = 0;
                out.print("Resources loading");
                while (true) {
                    if (counter == 4) {
                        counter = 0;
                        out.print("\rResources loading");
                    }
                    out.print(".");
                    sleep(250);
                    counter++;
                }
            }
        });
        spinner.setDaemon(true);

        List<ClientResourceLookup> resources = null;
        validate(path);
        try {
            spinner.start();
            resources = session.resourcesService().resources()
                    .parameter(FOLDER_URI, "".equals(path) ? "/" : path)
                    .search().getEntity().getResourceLookups();
        } catch (ResourceNotFoundException e) {
            out.print("\r");
            throw new JrsResourceNotFoundException(path);
        } finally {
            spinner.stop();
        }

        List<String> list = newArrayList();
        TreeConverter converter = new TreeConverter();

        for (ClientResourceLookup lookup : resources) {
            list.add(lookup.getUri());
        }

        TreeNode tree = converter.toTree(list, path);
        out.print("\r");
        tree.print();
    }
}
