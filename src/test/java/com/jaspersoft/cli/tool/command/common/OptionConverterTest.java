package com.jaspersoft.cli.tool.command.common;

import com.jaspersoft.cli.tool.command.impl.ShowCommand.OutputFormat;
import com.jaspersoft.cli.tool.exception.UnknownFormatOptionException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for {@link OptionConverter}
 */
public class OptionConverterTest {

    private OptionConverter converter;

    @BeforeMethod
    public void before() {
        converter = new OptionConverter();
    }

    @Test
    public void should_return_proper_option_format_json() {
        Assert.assertEquals(converter.convert("json"), OutputFormat.JSON);
    }

    @Test
    public void should_return_proper_option_format_text() {
        Assert.assertEquals(converter.convert("text"), OutputFormat.TEXT);
    }

    @Test
    public void should_return_proper_option_format_list_text() {
        Assert.assertEquals(converter.convert("list"), OutputFormat.LIST_TEXT);
    }

    @Test(expectedExceptions = UnknownFormatOptionException.class)
    public void should_() {
        converter.convert("piu!piu!");
    }

    @AfterMethod
    public void after() {
        converter = null;
    }
}