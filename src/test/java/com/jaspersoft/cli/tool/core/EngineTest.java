package com.jaspersoft.cli.tool.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link Engine}
 */
@PrepareForTest({Engine.class, BasicParser.class, CommandLine.class})
public class EngineTest extends PowerMockTestCase {

    @Mock
    private BasicParser parserMock;

    @Mock
    private CommandLine cmdLineMock;

    @Mock
    private ClientConfigurationSessionFactory factoryMock;

    @Mock
    private Session sessionMock;


    private Engine engine;
    private String[] args;

    @BeforeMethod
    public void before() {
        initMocks(this);
        args = new String[]{
                "import",
                "-url", "http://54.242.71.33/jasperserver-pro",
                "-u", "superuser",
                "-p", "superuser",
                "-f", "/folder/arch.zip"};
    }

    @Test (enabled = false)
    public void should_instantiate_options_and_args() {

        /** when **/
        engine = new Engine();

        /** than **/
        Options options = (Options) getInternalState(engine, "options");
        String[] arguments = (String[]) getInternalState(engine, "args");

        assertTrue(options.hasOption("url"));
        assertTrue(options.hasOption("u"));
        assertTrue(options.hasOption("p"));
        assertTrue(options.hasOption("f"));

        assertEquals(options.getOption("url"), new Option("url", true, "jrs url"));
        assertEquals(options.getOption("u").getDescription(), "username");
        assertEquals(options.getOption("p").getLongOpt(), "password");

        assertSame(arguments.length, 9);
        assertEquals(arguments[0], "import");
        assertEquals(arguments[1], "-url");
        assertEquals(arguments[2], "http://54.242.71.33/jasperserver-pro");
        assertEquals(arguments[3], "-u");
        assertEquals(arguments[4], "superuser");
    }

    @Test(enabled = false)
    public void should_process_command_and_options() throws Exception {

        /** given **/
        PowerMockito.whenNew(BasicParser.class).withNoArguments().thenReturn(parserMock);
        PowerMockito.doReturn(cmdLineMock).when(parserMock).parse(any(Options.class), any(String[].class));
        PowerMockito.doReturn(new String[]{"import"}).when(cmdLineMock).getArgs();
        PowerMockito.doReturn(new Option[]{new Option("u", true, "username")}).when(cmdLineMock).getOptions();

        PowerMockito.whenNew(ClientConfigurationSessionFactory.class).withNoArguments().thenReturn(factoryMock);
        PowerMockito.doReturn(sessionMock).when(factoryMock).configure(anyString(), anyString(), anyString());

        PowerMockito.suppress(method(ServiceOperation.class, "importResource"));

        /** when **/
        engine = new Engine();
        engine.run(args);

        /** than **/
        // ...
    }

    @AfterMethod
    public void after() {
        Mockito.reset(parserMock, cmdLineMock, factoryMock, sessionMock);
        args = null;
        engine = null;
    }
}