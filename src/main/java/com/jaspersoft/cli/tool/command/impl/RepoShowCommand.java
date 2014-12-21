package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.common.tree.TreeConverter;
import com.jaspersoft.cli.tool.command.common.tree.TreeNode;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.cli.tool.exception.WrongPathException;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.FOLDER_URI;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "resources")
public class RepoShowCommand extends ShowCommand {

    @Parameter(names = "--path", required = false)
    private String path = "";

    public RepoShowCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        try {
            List<ClientResourceLookup> resources = SessionFactory.getInstance()
                    .resourcesService().resources()
                    .parameter(FOLDER_URI, path.equals("") ? "/" : path).search()
                    .entity().getResourceLookups();

            List<String> list = newArrayList();
            TreeConverter converter = new TreeConverter();

            for (ClientResourceLookup lookup : resources) {
                list.add(lookup.getUri());
            }
            TreeNode tree = converter.toTree(list, path);
            tree.print();
            return super.execute();
        } catch (ResourceNotFoundException e) {
            throw new WrongPathException();
        }
    }
}
