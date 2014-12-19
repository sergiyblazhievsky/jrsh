package com.jaspersoft.cli.tool.command.impl;

import com.jaspersoft.cli.tool.command.impl.ShowCommand.OutputFormat;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Unit tests for {@link ShowCommand}
 */
public class ShowCommandTest {
    private ShowCommand showCmd;

    @BeforeMethod
    public void before() {
        showCmd = new ShowCommand("show", 2);
    }

    @Test
    public void should_init_format_field() {
        assertEquals(showCmd.getFormat(), OutputFormat.LIST_TEXT);
    }

    @Test
    public void should_return_null() {
        assertNull(showCmd.execute());
    }

    @AfterMethod
    public void after() {
        showCmd = null;
    }
}