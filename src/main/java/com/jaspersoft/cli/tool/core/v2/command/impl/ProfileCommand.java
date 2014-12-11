package com.jaspersoft.cli.tool.core.v2.command.impl;

import com.jaspersoft.cli.tool.core.v2.command.AbstractCommand;
import com.jaspersoft.cli.tool.core.v2.command.Command;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.apache.commons.cli.Option;

/**
 * @author Alex Krasnyanskiy
 */
public class ProfileCommand extends AbstractCommand<Void>{

    public ProfileCommand(Command masterCommand, Session clientSession, Option... options) {
        super(masterCommand, clientSession, options);
    }

    @Override
    public Void execute() {
        return null;
    }
}
