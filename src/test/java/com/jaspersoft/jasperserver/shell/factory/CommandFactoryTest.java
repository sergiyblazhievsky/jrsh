package com.jaspersoft.jasperserver.shell.factory;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.ExitCommand;
import com.jaspersoft.jasperserver.shell.command.ExportCommand;
import com.jaspersoft.jasperserver.shell.command.HelpCommand;
import com.jaspersoft.jasperserver.shell.command.LoginCommand;
import com.jaspersoft.jasperserver.shell.command.LogoutCommand;
import com.jaspersoft.jasperserver.shell.exception.parser.NoSuchCommandException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class CommandFactoryTest {

    @Test
    public void should_return_proper_command_class() {
        Command help = CommandFactory.create("help");
        Command export = CommandFactory.create("export");
        Command login = CommandFactory.create("login");
        Command logout = CommandFactory.create("logout");
        Command exit = CommandFactory.create("exit");

        Assert.assertTrue(instanceOf(HelpCommand.class).matches(help));
        Assert.assertTrue(instanceOf(ExportCommand.class).matches(export));
        Assert.assertTrue(instanceOf(LogoutCommand.class).matches(logout));
        Assert.assertTrue(instanceOf(LoginCommand.class).matches(login));
        Assert.assertTrue(instanceOf(ExitCommand.class).matches(exit));
    }

    @Test(expectedExceptions = NoSuchCommandException.class)
    public void should_throw_an_exception_if_such_command_does_not_exist() {
        Command unknown = CommandFactory.create("unknown");
    }
}