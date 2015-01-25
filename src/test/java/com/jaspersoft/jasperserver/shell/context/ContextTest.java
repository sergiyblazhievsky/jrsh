package com.jaspersoft.jasperserver.shell.context;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link ContextTest}
 */
public class ContextTest {

    private Context context;

    @BeforeMethod
    public void before() {
        context = new Context();
    }

    @Test
    public void should_return_dictionary_with_proper_amount_of_commands_name() {
        List<String> dictionary = context.getDictionary();
        Assert.assertSame(dictionary.size(), 10);
    }

    @Test
    public void should_contain_proper_command_names() {
        List<String> dictionary = context.getDictionary();

        Assert.assertTrue(dictionary.contains("help"));
        Assert.assertTrue(dictionary.contains("?"));

        Assert.assertTrue(dictionary.contains("import"));
        Assert.assertTrue(dictionary.contains("export"));


        Assert.assertTrue(dictionary.contains("logout"));
        Assert.assertTrue(dictionary.contains("login"));

        Assert.assertTrue(dictionary.contains("profile"));
        Assert.assertTrue(dictionary.contains("session"));
        Assert.assertTrue(dictionary.contains("exit"));
    }

    @Test
    public void should_contain_proper_command_description() {
        Map<String, String> description = context.getCmdDescription();

        String descForImport = description.get("import");
        String descForExport = description.get("export");

        Assert.assertEquals(descForImport, "Imports configuration into JasperReportsServer.");
        Assert.assertEquals(descForExport, "Exports configuration of JasperReportsServer.");
    }

    @AfterMethod
    public void after() {
        context = null;
    }
}