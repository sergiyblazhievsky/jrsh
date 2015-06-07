package com.jaspersoft.jasperserver.jrsh.core.script;//package com.jaspersoft.jasperserver.jrsh.core.script;
//
//import com.jaspersoft.jasperserver.jrsh.core.common.ArgumentConverter;
//import com.jaspersoft.jasperserver.jrsh.core.common.Script;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.List;
//
//public class ParameterConverterTest {
//
//    @Test
//    public void shouldReturnProperScriptWhichContainOnlyOneHelpOperation() {
//        /* Given */
//        String[] parameters = new String[]{};
//        /* When */
//        Script script = ArgumentConverter.convertToData(parameters);
//        List<String> source = script.getSource();
//        /* Then */
//        Assert.assertTrue(source.size() == 1);
//        Assert.assertTrue(source.contains("help"));
//    }
//
//    @Test
//    public void shouldConvertParametersIntoTheScriptWhichConsistsOfSingleLine() {
//        /* Given */
//        String[] parameters = new String[]{"help", "login"};
//        /* When */
//        Script script = ArgumentConverter.convertToData(parameters);
//        List<String> source = script.getSource();
//        /* Then */
//        Assert.assertEquals("help login", source.get(0));
//    }
//
//    @Test
//    public void shouldConvertParametersIntoTheScriptWhichConsistsOfTwoLines() {
//        /* Given */
//        String[] parameters = new String[]{"user|org%password@localhost", "export", "all"};
//        /* When */
//        Script script = ArgumentConverter.convertToData(parameters);
//        List<String> source = script.getSource();
//        /* Then */
//        Assert.assertEquals("login user|org%password@localhost", source.get(0));
//        Assert.assertEquals("export all", source.get(1));
//        Assert.assertEquals(2, source.size());
//    }
//
//    @Test
//    public void shouldConvertParametersIntoTheScriptWhichContainAStringLine() {
//        /* Given */
//        String[] parameters = new String[]{"user|org%password@localhost"};
//        /* When */
//        Script script = ArgumentConverter.convertToData(parameters);
//        List<String> source = script.getSource();
//        /* Then */
//        Assert.assertEquals("login user|org%password@localhost", source.get(0));
//        Assert.assertEquals(1, source.size());
//    }
//
//    @Test
//    public void shouldConvertParameters() {
//        /* Given */
//        String[] parameters = new String[]{"dummy_operation", "-p1", "-4", "-p2", "xy"};
//        /* When */
//        Script script = ArgumentConverter.convertToData(parameters);
//        List<String> source = script.getSource();
//        /* Then */
//        Assert.assertEquals("dummy_operation -p1 -4 -p2 xy", source.get(0));
//        Assert.assertEquals(1, source.size());
//    }
//}