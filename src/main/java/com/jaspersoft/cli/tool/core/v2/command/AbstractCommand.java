package com.jaspersoft.cli.tool.core.v2.command;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import lombok.Data;
import org.apache.commons.cli.Option;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author A. Krasnyanskiy
 */
@Data
public abstract class AbstractCommand<T> implements Command<T> {

    private String commandName;
    private List<Command> subcommands = emptyList();
    protected Command masterCommand; // mother command
    protected final Session clientSession;
    protected final Option[] options;

    public AbstractCommand(Command masterCommand, Session clientSession, Option... options) {
        this.masterCommand = masterCommand;
        this.clientSession = clientSession;
        this.options = options;
    }
}
