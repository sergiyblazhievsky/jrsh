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
//@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "jrs root command")
public class JrsCommand extends AbstractCommand<Void> {

    @Parameter(names = {"-s", "--server"}, required = false)
    private String url;
    @Parameter(names = {"-u", "--username"}, required = false)
    private String username;
    @Parameter(names = {"-p", "--password"}, required = false)
    private String password;
    @Parameter(names = {"-d", "--debug"}, required = false)
    private boolean debug;

    public JrsCommand(String commandName, Integer level){
        super(commandName, level);
    }

    @Override
    public Void execute() {
        SessionFactory.create(url, username, password);
        return null;
    }
}