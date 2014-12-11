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
public class JrsCommand extends AbstractCommand<Void> {

    public JrsCommand(Command masterCommand, Session clientSession, Option... options) {
        super(masterCommand, clientSession, options);
    }

    @Override
    public Void execute() {
        System.out.println("root command");
        return null;
    }
}
