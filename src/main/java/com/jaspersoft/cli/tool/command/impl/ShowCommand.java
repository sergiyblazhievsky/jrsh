package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.common.tree.TreeConverter;
import com.jaspersoft.cli.tool.command.common.tree.TreeNode;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter;
import lombok.Data;

import java.util.List;

import static com.beust.jcommander.internal.Lists.newArrayList;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Parameters(commandDescription = "list")
public class ShowCommand extends AbstractCommand<Void> {

    @Parameter(names = "--path", required = false)
    private String path = "";

    @Override
    public Void execute() {
        List<ClientResourceLookup> resources = clientSession
                .resourcesService()
                .resources().parameter(ResourceSearchParameter.FOLDER_URI, path.equals("") ? "/" : path)
                .search().entity().getResourceLookups();

        List<String> list = newArrayList();
        TreeConverter converter = new TreeConverter();

        for (ClientResourceLookup lookup : resources) {
            list.add(lookup.getUri());
        }

        TreeNode tree = converter.toTree(list, path);
        tree.print();
        return null;
    }
}
