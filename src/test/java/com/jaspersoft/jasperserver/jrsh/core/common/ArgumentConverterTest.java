package com.jaspersoft.jasperserver.jrsh.core.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ArgumentConverterTest {

    @Test
    public void shouldReturnProperScriptWhichContainOnlyOneHelpOperation() {
        /* Given */
        String[] args = new String[]{};
        /* When */
        Data data = ArgumentConverter.convertToData(args);
        List<String> source = data.getSource();
        /* Then */
        Assert.assertTrue(source.size() == 1);
        Assert.assertTrue(source.contains("help"));
    }

    @Test
    public void shouldConvertArgumentsIntoTheScriptWhichConsistsOfSingleLine() {
        /* Given */
        String[] args = new String[]{"help", "login"};
        /* When */
        Data data = ArgumentConverter.convertToData(args);
        List<String> source = data.getSource();
        /* Then */
        Assert.assertEquals("help login", source.get(0));
    }

    @Test
    public void shouldConvertArgumentsIntoTheScriptWhichConsistsOfTwoLines() {
        /* Given */
        String[] args = new String[]{"user|org%password@localhost", "export", "all"};
        /* When */
        Data data = ArgumentConverter.convertToData(args);
        List<String> source = data.getSource();
        /* Then */
        Assert.assertEquals("login user|org%password@localhost", source.get(0));
        Assert.assertEquals("export all", source.get(1));
        Assert.assertEquals(2, source.size());
    }

    @Test
    public void shouldConvertArgumentsIntoTheScriptWhichContainAStringLine() {
        /* Given */
        String[] args = new String[]{"user|org%password@localhost"};
        /* When */
        Data data = ArgumentConverter.convertToData(args);
        List<String> source = data.getSource();
        /* Then */
        Assert.assertEquals("login user|org%password@localhost", source.get(0));
        Assert.assertEquals(1, source.size());
    }

    @Test
    public void shouldConvertArgumentsIntoScript() {
        /* Given */
        String[] args = new String[]{"dummy_operation", "-p1", "-4", "-p2", "xy"};
        /* When */
        Data data = ArgumentConverter.convertToData(args);
        List<String> source = data.getSource();
        /* Then */
        Assert.assertEquals("dummy_operation -p1 -4 -p2 xy", source.get(0));
        Assert.assertEquals(1, source.size());
    }
}