package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
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
    @Parameter(names = {"-p", "--password"}, required = true)
    private String password;

    @Override
    public Void execute() {
        RestClientConfiguration configuration = new RestClientConfiguration(url);
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        jrsRestClientSession = client.authenticate(username, password); // pass up session to parent
        return null;
    }
}