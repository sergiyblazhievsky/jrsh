package com.jaspersoft.cli.tool.command;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Abstract holder for {@link com.jaspersoft.jasperserver.jaxrs.client.core.Session} instance, which is
 * used by all concrete commands.
 *
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public abstract class AbstractCommand<T> implements Command<T>, Comparable<AbstractCommand> {

    private boolean isActive;
    private int order;
    protected static Session jrsRestClientSession;

    @Override
    public int compareTo(AbstractCommand command) {
        return this.order - command.order;
    }
}
