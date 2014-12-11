package com.jaspersoft.cli.tool.core.v2;

import com.jaspersoft.cli.tool.core.v2.command.Command;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.cli.tool.core.v2.command.factory.CommandFactory.create;
import static com.jaspersoft.cli.tool.core.v2.common.ApplicationArgumentsConverter.toCmdNames;
import static com.jaspersoft.cli.tool.core.v2.common.ApplicationArgumentsConverter.toOptions;

/**
 * @author Alexander Krasnyanskiy
 */
public class CliTool {

    private Options predefinedOptions = new Options();
    private List<Command> commands = new ArrayList<>();

    public CliTool() {

        predefinedOptions.addOption("s", "server", true, "JasperReportServer URL");
        predefinedOptions.addOption("u", "user", true, "JRS username");
        predefinedOptions.addOption("o", "organization", true, "JRS tenant name (commercial edition only)");
        predefinedOptions.addOption("p", "password", true, "JRS password");
        predefinedOptions.addOption("f", "file", true, "zip file");
        predefinedOptions.addOption("path", true, "the path to the folder form which we start printing tree");
    }

    void execute(String[] args) {
        commands.addAll(create(toCmdNames(args), null, toOptions(args)));
        for (Command command : commands) {
            command.execute();
        }
    }
}
