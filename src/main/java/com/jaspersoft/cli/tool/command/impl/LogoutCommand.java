package com.jaspersoft.cli.tool.command.impl;

import com.jaspersoft.cli.tool.command.AbstractCommand;
import lombok.Data;

/**
 * @author Alex Krasnyanskiy
 */
@Data
public class LogoutCommand extends AbstractCommand<Void>{
    @Override
    public Void execute() {
        return null;
    }
}
