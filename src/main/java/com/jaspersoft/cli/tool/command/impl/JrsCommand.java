package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import lombok.Data;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@Parameters(commandDescription = "jrs root command")
public class JrsCommand extends AbstractCommand<Void> {

    @Parameter(names = {"-s", "--server"}, required = false)
    private String url;
    @Parameter(names = {"-u", "--username"}, required = false)
    private String username;
    @Parameter(names = {"-p", "--password"}, required = false)
    private String password;

    @Override
    public Void execute() {
        clientSession = SessionFactory.create(url, username, password);
        return null;
    }
}