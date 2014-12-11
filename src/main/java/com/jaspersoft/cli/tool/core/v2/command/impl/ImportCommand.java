package com.jaspersoft.cli.tool.core.v2.command.impl;

import com.jaspersoft.cli.tool.core.v2.command.AbstractCommand;
import com.jaspersoft.cli.tool.core.v2.command.Command;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import lombok.Data;
import org.apache.commons.cli.Option;

/**
 * @author Alexander Krasnyanskiy
 */
@Data
public class ImportCommand extends AbstractCommand<String> {

    public ImportCommand(Command masterCommand, Session clientSession, Option... options) { // the object on which actions execute or with which actions are performed
        super(masterCommand, clientSession, options);
    }

    @Override
    public String execute() {
        System.out.println("import");
        return "";
    }
}
