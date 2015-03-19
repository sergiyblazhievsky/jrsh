package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceListWrapper;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;
import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.support.TreeConverter;
import com.jaspersoft.jasperserver.shell.command.support.TreeNode;
import com.jaspersoft.jasperserver.shell.exception.server.JrsResourceNotFoundException;
import com.jaspersoft.jasperserver.shell.SessionFactory;
import com.jaspersoft.jasperserver.shell.Parameter;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.FOLDER_URI;
import static com.jaspersoft.jasperserver.shell.validator.RepositoryPathValidatorUtil.validate;
import static java.lang.String.format;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class ShowCommand extends Command {

    private Session session;

    public ShowCommand() {
        name = "show";
        description = "Show information about JRS or print configuration tree.";
        usageDescription = "\tUsage:  show repo <path>\n\t\t\tshow server-info";
        parameters.add(new Parameter().setName("anonymous").setOptional(true));
        parameters.add(new Parameter().setName("server-info").setOptional(true));
        parameters.add(new Parameter().setName("repo").setOptional(true));
    }

    @Override
    public void run() {

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
            out.println("See help for 'show' command.");
        }
    }

    private void printInfo() {
        ServerInfo info = session.serverInfoService().details().getEntity();
        out.printf("\nExpiration: \u001B[31m%s\u001B[0m\n" +
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
//        Thread spinner = new Thread(new Runnable() {
//            @SneakyThrows
//            public void run() {
//                int counter = 0;
//                out.print("Resources loading");
//                while (true) {
//                    if (counter == 4) {
//                        counter = 0;
//                        out.print("\rResources loading");
//                    }
//                    out.print(".");
//                    sleep(250);
//                    counter++;
//                }
//            }
//        });
//        spinner.setDaemon(true);
        List<ClientResourceLookup> resources;

        int directoriesNumber = 0;
        int filesNumber = 0;

        validate(path);
        try {
//            spinner.start();
            ClientResourceListWrapper tmp = session.resourcesService().resources().parameter(FOLDER_URI, path).parameter(ResourceSearchParameter.LIMIT, "3500").search().getEntity();

            if (tmp != null) {
                resources = tmp.getResourceLookups();
            } else {
                // then we have an empty folder here
                out.println(path.substring(path.lastIndexOf('/')));
                out.println(format("\n%d directories, %d files", directoriesNumber, filesNumber));
                return;
                //throw new NoRepositoryContentException();
            }

        } catch (ResourceNotFoundException e) {
            out.print("\r");
            throw new JrsResourceNotFoundException(path);
        } /*finally {
            spinner.stop();
        }*/


        List<String> list = newArrayList();
        TreeConverter converter = new TreeConverter();
        for (ClientResourceLookup lookup : resources) {
            list.add(lookup.getUri());
            if ("folder".equals(lookup.getResourceType())) {
                directoriesNumber++;
            } else {
                filesNumber++;
            }
        }
        TreeNode tree = converter.toTree(list, path);
        out.print("\r");
        tree.print();
        out.println(format("\n%d directories, %d files", directoriesNumber, filesNumber));
    }
}
