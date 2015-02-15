package com.jaspersoft.jasperserver.shell.factory;

import com.jaspersoft.jasperserver.shell.command.ClearScreenCommand;
import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.ExitCommand;
import com.jaspersoft.jasperserver.shell.command.HelpCommand;
import com.jaspersoft.jasperserver.shell.command.ImportCommand;
import com.jaspersoft.jasperserver.shell.command.LoginCommand;
import com.jaspersoft.jasperserver.shell.command.LogoutCommand;
import com.jaspersoft.jasperserver.shell.command.ProfileCommand;
import com.jaspersoft.jasperserver.shell.command.ReplicateCommand;
import com.jaspersoft.jasperserver.shell.command.SessionCommand;
import com.jaspersoft.jasperserver.shell.command.ShowCommand;
import com.jaspersoft.jasperserver.shell.command.ExportCommand;
import com.jaspersoft.jasperserver.shell.exception.parser.NoSuchCommandException;

/**
 * @author Alexander Krasnyanskiy
 */
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
