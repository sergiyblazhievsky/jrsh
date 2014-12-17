package com.jaspersoft.cli.tool.command;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Abstract holder for {@link Session} instance, which is
 * used by all concrete commands.
 *
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public abstract class AbstractCommand<T> implements Command<T>, Comparable<AbstractCommand> {

    private boolean isActive;
    private int level;
    protected static Session clientSession; // todo: move to factory

    @Override
    public int compareTo(AbstractCommand command) {
        return this.level - command.level;
    }
}
