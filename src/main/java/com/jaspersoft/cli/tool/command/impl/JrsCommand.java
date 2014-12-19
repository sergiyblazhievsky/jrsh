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
    @Parameter(names = {"-s", "--server"}, required = true)
    private String url;
    @Parameter(names = {"-u", "--username"}, required = true)
    private String username;
    @Parameter(names = {"-p", "--password"}, required = true /*password = true*/)
    private String password;
    @Parameter(names = {"-o", "--organization"}, required = false)
    private String organization;
    @Parameter(names = {"-d", "--debug"}, required = false)
    private boolean debug;

    public JrsCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        SessionFactory.create(url, username, password, organization);
        return null;
    }
}