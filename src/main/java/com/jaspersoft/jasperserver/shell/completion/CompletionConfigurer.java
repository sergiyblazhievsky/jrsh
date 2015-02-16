package com.jaspersoft.jasperserver.shell.completion;

import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.reader.ProfileReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.StringsCompleter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
public class CompletionConfigurer {

    private final static String FILE = "/conf/profiles.yml";
    private AggregateCompleter aggregator;

    public CompletionConfigurer() {
        StringsCompleter exit = new StringsCompleter("exit");
        StringsCompleter clear = new StringsCompleter("clear");
        StringsCompleter session = new StringsCompleter("session");
        StringsCompleter logout = new StringsCompleter("logout");

        StringsCompleter profile = new StringsCompleter("profile");
        GeneralCommandParameterCompleter profileParams = new GeneralCommandParameterCompleter(asList("save", "load", "list"));

        StringsCompleter login = new StringsCompleter("login");
        GeneralCommandParameterCompleter loginParams = new GeneralCommandParameterCompleter(asList("--server",
                "--username", "--password"));

        StringsCompleter show = new StringsCompleter("show");
        GeneralCommandParameterCompleter showParams = new GeneralCommandParameterCompleter(asList("repo", "server-info"));

        StringsCompleter replicate = new StringsCompleter("replicate");
            ReplicateCommandCommandParameterCompleter replicateParams =
                new ReplicateCommandCommandParameterCompleter(getProfileNames());


        StringsCompleter export = new StringsCompleter("export");
        GeneralCommandParameterCompleter exportParams = new GeneralCommandParameterCompleter(asList("to",
                "without-access-events", "with-audit-events", "with-monitoring-events", "with-events",
                "with-users-and-roles", "with-repository-permissions"));

        StringsCompleter importCmd = new StringsCompleter("import");
        GeneralCommandParameterCompleter importParams = new GeneralCommandParameterCompleter(asList("with-audit-events",
                "with-access-events", "with-monitoring-events", "with-events", "with-update",
                "with-skip-user-update"));

        StringsCompleter help = new StringsCompleter("help");
        GeneralCommandParameterCompleter helpParams = new GeneralCommandParameterCompleter(asList("login", "logout", "import",
                "export", "exit", "show", "session", "replicate", "profile"));


        ArgumentCompleter loginCompleter = new ArgumentCompleter(login, loginParams);
        ArgumentCompleter exportCompleter = new ArgumentCompleter(export, exportParams);
        ArgumentCompleter helpCompleter = new ArgumentCompleter(help, helpParams);
        ArgumentCompleter showCompleter = new ArgumentCompleter(show, showParams);
        ArgumentCompleter importCompleter = new ArgumentCompleter(importCmd, importParams);
        ArgumentCompleter profileCompleter = new ArgumentCompleter(profile, profileParams);
        ArgumentCompleter replicateCompleter = new ArgumentCompleter(replicate, replicateParams);

        aggregator = new AggregateCompleter(exit, clear, logout, replicateCompleter, profileCompleter, session, loginCompleter, importCompleter, showCompleter, exportCompleter, helpCompleter);
    }

    public AggregateCompleter getAggregator() {
        return aggregator;
    }

    /**
     * Profiles pre-loaded profile names. todo: move the method to the separate class!
     *
     * @return list
     */
    private static List<String> getProfileNames() {
        File file = new File(getProperty("user.dir"));
        List<String> profileNames = new ArrayList<>();
        try {
            Set<Profile> profiles = new ProfileReader(file + FILE).read().getProfiles();
            for (Profile p : profiles) {
                String name = p.getName();
                if (name != null && !name.isEmpty()) {
                    profileNames.add(name);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return profileNames;
    }


}
