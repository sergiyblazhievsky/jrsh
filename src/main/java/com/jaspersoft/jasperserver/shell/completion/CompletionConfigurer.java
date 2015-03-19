package com.jaspersoft.jasperserver.shell.completion;

import com.jaspersoft.jasperserver.shell.completion.completer.CommandCommonParameterCompleter;
import com.jaspersoft.jasperserver.shell.completion.completer.CustomFileCompleter;
import com.jaspersoft.jasperserver.shell.completion.completer.CustomParameterCompleter;
import com.jaspersoft.jasperserver.shell.completion.completer.ParameterCompleter;
import com.jaspersoft.jasperserver.shell.completion.completer.PathCompleter;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.NullCompleter;
import jline.console.completer.StringsCompleter;

import static com.jaspersoft.jasperserver.shell.profile.ProfileUtil.list;
import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
public class CompletionConfigurer {

    private AggregateCompleter aggregator;

    // hack
    public static StringsCompleter available = new StringsCompleter(list()); // FIXME: Delete this line!

    public CompletionConfigurer() {

        Completer nil = new NullCompleter();

        Completer exit = new StringsCompleter("exit");
        Completer clear = new StringsCompleter("clear");
        Completer session = new StringsCompleter("session");
        Completer logout = new StringsCompleter("logout");

        /**
         * Profile Completer
         */
        Completer profile = new StringsCompleter("profile");
        Completer save = new StringsCompleter("save");
        Completer load = new StringsCompleter("load");
        Completer default_ = new StringsCompleter("default");
        Completer list = new StringsCompleter("list");



        Completer login = new StringsCompleter("login");
        Completer loginParameters = new CommandCommonParameterCompleter("--server", "--username", "--password");

        Completer show = new StringsCompleter("show");
        Completer showParameters = new CommandCommonParameterCompleter("repo", "server-info");


        /**
         * Replicate Completer
         */
        Completer replicate = new StringsCompleter("replicate");
        Completer firstProfileName = new ParameterCompleter(list());
        Completer direction = new ParameterCompleter("to");
        Completer secondProfileName = new CustomParameterCompleter(list());


        /**
         * Export Completer
         */
        Completer export = new StringsCompleter("export");
        Completer all = new StringsCompleter("all");
        Completer repo = new StringsCompleter("repo");
        Completer role = new StringsCompleter("role");
        Completer user = new StringsCompleter("user");
        Completer path = new PathCompleter();


        /**
         * Import Completer
         */
        Completer import_ = new StringsCompleter("import");
        Completer file = new FileNameCompleter();
        Completer events = new StringsCompleter("with-audit-events", "with-access-events", "with-monitoring-events", "with-events", "with-update", "with-skip-user-update");


        /**
         * Help Completer
         */
        Completer help = new StringsCompleter("help");
        Completer helpParameters = new StringsCompleter(asList("login", "logout", "import", "export", "exit", "show", "session", "replicate", "profile"));


        Completer loginCompleter = new ArgumentCompleter(login, loginParameters);
        Completer helpCompleter = new ArgumentCompleter(help, helpParameters, nil);
        Completer showCompleter = new ArgumentCompleter(show, showParameters, new PathCompleter(), /*new FolderRepositoryPathCompleter(),*/ nil);
        Completer importCompleter = new ArgumentCompleter(import_, file, events);
        Completer replicateCompleter = new ArgumentCompleter(replicate, firstProfileName, direction, secondProfileName, nil);

        aggregator = new AggregateCompleter(exit,

                new ArgumentCompleter(export, nil),

                new ArgumentCompleter(export, all, nil),
                new ArgumentCompleter(export, user, nil),
                new ArgumentCompleter(export, role, nil),

                new ArgumentCompleter(export, repo, nil),
                new ArgumentCompleter(export, repo, path, nil),
                new ArgumentCompleter(export, repo, path, events),

                new ArgumentCompleter(export, repo, path, new StringsCompleter("to"), new CustomFileCompleter(), events/*, new NullCompleter()*/),


                clear, logout, replicateCompleter,

                new ArgumentCompleter(profile, nil),
                new ArgumentCompleter(profile, save, nil),
                new ArgumentCompleter(profile, load, nil),
                new ArgumentCompleter(profile, load, available, nil),
                new ArgumentCompleter(profile, default_, nil),
                new ArgumentCompleter(profile, default_, available, nil),
                new ArgumentCompleter(profile, list, nil),
                new ArgumentCompleter(profile, list, available /*, new NullCompleter()*/),


                session, loginCompleter,
                importCompleter, showCompleter, helpCompleter);
    }

    public AggregateCompleter getAggregator() {
        return aggregator;
    }
}