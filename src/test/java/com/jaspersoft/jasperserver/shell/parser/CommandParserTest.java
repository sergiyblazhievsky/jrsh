package com.jaspersoft.jasperserver.shell.parser;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.ImportCommand;
import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.validator.ParameterValidator;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Queue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.shell.parser.CommandParser}
 */
public class CommandParserTest {

    private CommandParser parser;

    @BeforeMethod
    public void before() {
        parser = new CommandParser(new ParameterValidator());
        parser.setContext(new Context());
    }

    @Test
    public void should_return_execution_queue_which_should_contains_two_commands() {
        Queue<Command> queue = parser.parse(
                "login --server http://localhost:8080/jasperserver-pro --username superuser --password superuser " +
                        "import /Users/alexkrasnyaskiy/Desktop/jrsh/imp1.zip");

        Assert.assertTrue(!queue.isEmpty());
        Assert.assertTrue(queue.size() == 2);
    }

    @Test
    public void should_return_queue_with_only_one_configured_command() {

        /** Given **/
        Command expected = new ImportCommand();

        Parameter anon = expected.getParameters().get(0);
        anon.getValues().add("/Users/alexkrasnyaskiy/Desktop/jrsh/imp1.zip");
        anon.setAvailable(true);

        Parameter withAudit = expected.getParameters().get(1);
        withAudit.getValues().add("with-audit-events");
        withAudit.setAvailable(true);


        /** When **/
        Queue<Command> queue = parser.parse("import /Users/alexkrasnyaskiy/Desktop/jrsh/imp1.zip with-audit-events");


        /** Then **/
        Command retrieved = queue.poll();
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(retrieved, expected);
    }

    @Test
    public void should_return_queue_with_three_commands() {
        Queue<Command> queue = parser.parse(
                "--server http://localhost:8080/jasperserver-pro --username superuser --password superuser " +
                        "import /Users/alexkrasnyaskiy/Desktop/jrsh/imp1.zip logout");

        Assert.assertNotNull(queue);
        Assert.assertSame(queue.size(), 3);
    }

    @AfterMethod
    public void after() {
        parser = null;
    }
}