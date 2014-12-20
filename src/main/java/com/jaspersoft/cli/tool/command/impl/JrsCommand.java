package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "root command")
public class JrsCommand extends AbstractCommand<Void> {

    @Parameter(names = {"-s", "--server"}, required = false /* FALSE because we could have HELP as next command */)
    private String url;
    @Parameter(names = {"-u", "--username"}, required = false)
    private String username;
    @Parameter(names = {"-p", "--password"}, required = false)
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
        if (url != null && username != null && password != null) {
            SessionFactory.create(url, username, password, organization);
        }
        return null;
    }
}