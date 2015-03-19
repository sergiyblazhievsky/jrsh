package com.jaspersoft.jasperserver.shell.factory;

import com.jaspersoft.jasperserver.shell.command.impl.ClearScreenCommand;
import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.impl.ExitCommand;
import com.jaspersoft.jasperserver.shell.command.impl.HelpCommand;
import com.jaspersoft.jasperserver.shell.command.impl.ImportCommand;
import com.jaspersoft.jasperserver.shell.command.impl.LoginCommand;
import com.jaspersoft.jasperserver.shell.command.impl.LogoutCommand;
import com.jaspersoft.jasperserver.shell.command.impl.ProfileCommand;
import com.jaspersoft.jasperserver.shell.command.impl.ReplicateCommand;
import com.jaspersoft.jasperserver.shell.command.impl.SessionCommand;
import com.jaspersoft.jasperserver.shell.command.impl.ShowCommand;
import com.jaspersoft.jasperserver.shell.command.impl.ExportCommand;
import com.jaspersoft.jasperserver.shell.exception.parser.NoSuchCommandException;

/**
 * @author Alexander Krasnyanskiy
 */
@Deprecated
public final class CommandFactory {

    private CommandFactory() {/*NOP*/}

    public static Command createCommand(String commandName) {
        switch (commandName) {
            case "exit": return new ExitCommand();
            case "login": return new LoginCommand();
            case "logout": return new LogoutCommand();
            case "import": return new ImportCommand();
            case "export": return new ExportCommand();
            case "clear": return new ClearScreenCommand();
            case "show": return new ShowCommand();
            case "replicate": return new ReplicateCommand();
            case "?": return new HelpCommand();
            case "help": return new HelpCommand();
            case "profile": return new ProfileCommand();
            case "session": return new SessionCommand();
            default: throw new NoSuchCommandException(commandName);
        }
    }
}
