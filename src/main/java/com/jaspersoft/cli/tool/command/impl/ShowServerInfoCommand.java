package com.jaspersoft.cli.tool.command.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static java.lang.System.out;

/**
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Parameters(commandDescription = "server info command")
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
        if (hasOptions()) {
            if (build) out.format("build: %s%n", info.getBuild());
            if (editionName) out.format("edition name: %s%n", info.getEditionName());
            if (edition) out.format("build: %s%n", info.getEdition());
            if (version) out.format("version: %s%n", info.getVersion());
            if (features) out.format("features: %s%n", info.getFeatures());
            if (licenseType) out.format("license type: %s%n", info.getLicenseType());
            if (datetimeFormatPattern) out.format("datetime format pattern: %s%n", info.getDatetimeFormatPattern());
        } else {
            out.format("build: %s%n", info.getBuild());
            out.format("edition name: %s%n", info.getEditionName());
            out.format("build: %s%n", info.getEdition());
            out.format("version: %s%n", info.getVersion());
            out.format("features: %s%n", info.getFeatures());
            out.format("license type: %s%n", info.getLicenseType());
            out.format("datetime format pattern: %s%n", info.getDatetimeFormatPattern());
        }
    }

    private boolean hasOptions() {
        return this.isBuild() || this.isEditionName() || this.isEdition() ||
                this.isVersion() || this.isFeatures() || this.isLicenseType() ||
                this.isDatetimeFormatPattern();
    }
}
