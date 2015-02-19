package com.jaspersoft.jasperserver.shell.factory;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.impl.ExitCommand;
import com.jaspersoft.jasperserver.shell.command.impl.ExportCommand;
import com.jaspersoft.jasperserver.shell.command.impl.HelpCommand;
import com.jaspersoft.jasperserver.shell.command.impl.LoginCommand;
import com.jaspersoft.jasperserver.shell.command.impl.LogoutCommand;
import com.jaspersoft.jasperserver.shell.exception.parser.NoSuchCommandException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class CommandFactoryTest {

    @Test
    public void should_return_proper_command_class() {
        Command help = CommandFactory.createCommand("help");
        Command export = CommandFactory.createCommand("export");
        Command login = CommandFactory.createCommand("login");
        Command logout = CommandFactory.createCommand("logout");
        Command exit = CommandFactory.createCommand("exit");

        Assert.assertTrue(instanceOf(HelpCommand.class).matches(help));
        Assert.assertTrue(instanceOf(ExportCommand.class).matches(export));
        Assert.assertTrue(instanceOf(LogoutCommand.class).matches(logout));
        Assert.assertTrue(instanceOf(LoginCommand.class).matches(login));
        Assert.assertTrue(instanceOf(ExitCommand.class).matches(exit));
    }

    @Test(expectedExceptions = NoSuchCommandException.class)
    public void should_throw_an_exception_if_such_command_does_not_exist() {
        Command unknown = CommandFactory.createCommand("unknown");
    }
}