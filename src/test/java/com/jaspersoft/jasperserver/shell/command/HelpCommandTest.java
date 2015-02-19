package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.command.impl.HelpCommand;
import com.jaspersoft.jasperserver.shell.context.Context;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.OutputStream;
import java.io.PrintStream;

import static java.lang.System.setOut;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.shell.command.impl.HelpCommand}
 */
@Test
@PrepareForTest({HelpCommand.class, PrintStream.class})
public class HelpCommandTest extends PowerMockTestCase {

    private HelpCommand help;
    private final static int COMMAND_AMOUNT = 10;

    @BeforeMethod
    public void before() {
        help = spy(new HelpCommand());
    }

    public void should_print_help() {

        /** Given **/
        PrintStream spy = spy(new PrintStream(new OutputStream() {
            public void write(int b) {}
        }));
        setOut(spy);
        help.setContext(new Context());


        /** When **/
        help.run();


        /** Then **/
        verify(spy, times(1)).println(anyString());
        verify(spy, times(COMMAND_AMOUNT)).printf(anyString(), anyVararg());
    }

    @AfterMethod
    public void after() {
        help = null;
    }
}