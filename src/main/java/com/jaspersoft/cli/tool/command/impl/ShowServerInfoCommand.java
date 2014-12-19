package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alex Krasnyanskiy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "serverInfo")
public class ShowServerInfoCommand extends ShowCommand {
    @Parameter(names = "--build", required = false)
    private boolean build;
    @Parameter(names = "--edition-name", required = false)
    private boolean editionName;
    @Parameter(names = "--edition", required = false)
    private boolean edition;
    @Parameter(names = "--version", required = false)
    private boolean version;
    @Parameter(names = "--features", required = false)
    private boolean features;
    @Parameter(names = "--license", required = false)
    private boolean licenseType;
    @Parameter(names = "--pattern", required = false)
    private boolean datetimeFormatPattern;

    public ShowServerInfoCommand(String commandName, Integer level) {
        super(commandName, level);
    }

    @Override
    public Void execute() {
        ServerInfo entity = SessionFactory.getInstance().serverInfoService().details().entity();
        print(entity);
        return null;
    }

    private void print(ServerInfo info) {
        if (build) System.out.format("build: %s%n", info.getBuild());
        if (editionName) System.out.format("edition name: %s%n", info.getEditionName());
        if (edition) System.out.format("build: %s%n", info.getEdition());
        if (version) System.out.format("version: %s%n", info.getVersion());
        if (features) System.out.format("features: %s%n", info.getFeatures());
        if (licenseType) System.out.format("license type: %s%n", info.getLicenseType());
        if (datetimeFormatPattern) System.out.format("datetime format pattern: %s%n", info.getDatetimeFormatPattern());
    }

//    private boolean hasOptions(ShowServerInfoCommand cmd) {
//        return cmd.isBuild() || cmd.isEditionName() || cmd.isEdition() ||
//                cmd.isVersion() || cmd.isFeatures() || cmd.isLicenseType() ||
//                cmd.isDatetimeFormatPattern();
//    }
}
