package com.jaspersoft.cli.tool.core.v2.command.impl;

import com.jaspersoft.cli.tool.core.v2.command.AbstractCommand;
import com.jaspersoft.cli.tool.core.v2.command.Command;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.apache.commons.cli.Option;

/**
 * @author Alex Krasnyanskiy
 */
public class ExportCommand extends AbstractCommand {

    public ExportCommand(Command masterCommand, Session clientSession, Option... options) {
        super(masterCommand, clientSession, options);
    }

    @Override
    public Object execute() {
        System.out.println("export");
        return null;
    }
}
