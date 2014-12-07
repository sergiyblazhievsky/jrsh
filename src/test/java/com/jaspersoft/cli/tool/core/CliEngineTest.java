package com.jaspersoft.cli.tool.core;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link CliEngine}
 */
public class CliEngineTest {

    private CliEngine engine;
    private String[] args;

    @BeforeMethod
    public void before() {
        args = new String[]{
                "import",
                "-url", "http://54.242.71.33/jasperserver-pro",
                "-u", "superuser",
                "-p", "superuser",
                "-f", "/folder/arch.zip"};
    }

    @Test
    public void should_instantiate_options_and_args() {

        /** when **/
        engine = new CliEngine(args);

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

    @AfterMethod
    public void after() {
        args = null;
        engine = null;
    }
}