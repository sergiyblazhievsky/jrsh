package com.jaspersoft.jasperserver.shell.context;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for {@link ContextTest}
 */
@Test
public class ContextTest {

    private Context context;

    @BeforeMethod
    public void before() {
        context = new Context();
    }

    public void should_return_dictionary_with_proper_amount_of_commands_name() {

        /** When **/
        List<String> dictionary = context.getDictionary();

        /** Then **/
        assertSame(dictionary.size(), 10);
    }

    public void should_contain_proper_command_names() {

        /** When **/
        List<String> dictionary = context.getDictionary();

        /** Then **/
        assertTrue(dictionary.contains("help"));
        assertTrue(dictionary.contains("?"));
        assertTrue(dictionary.contains("import"));
        assertTrue(dictionary.contains("export"));
        assertTrue(dictionary.contains("logout"));
        assertTrue(dictionary.contains("login"));
        assertTrue(dictionary.contains("profile"));
        assertTrue(dictionary.contains("session"));
        assertTrue(dictionary.contains("exit"));
    }

    public void should_contain_proper_command_description() {

        /** When **/
        Map<String, String> description = context.getCmdDescription();
        String descForImport = description.get("import");
        String descForExport = description.get("export");

        /** Then **/
        assertEquals(descForImport, "Imports configuration into JasperReportsServer.");
        assertEquals(descForExport, "Exports configuration of JasperReportsServer.");
    }

    @AfterMethod
    public void after() {
        context = null;
    }
}