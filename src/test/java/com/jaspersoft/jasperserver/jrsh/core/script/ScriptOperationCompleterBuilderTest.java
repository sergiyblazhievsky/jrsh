package com.jaspersoft.jasperserver.jrsh.core.script;//package com.jaspersoft.jasperserver.jrsh.core.script;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//
//import java.util.List;
//
//public class ScriptOperationCompleterBuilderTest {
//
//    @Test
//    public void shouldReturnToolScriptInstance() {
//        Script script = new ScriptBuilder(new String[]{
//                "any", "wrong", "operation", "should", "be",
//                "processed", "in", "the", "Tool", "mode"
//        }).convertToScript();
//        Assertions.assertThat(script).isInstanceOf(ToolOperationScript.class);
//    }
//
//    @Test
//    public void shouldReturnFileScript() {
//        Script script = new ScriptBuilder(new String[]{"--script", "my_script.jrs"}).convertToScript();
//        Assertions.assertThat(script).isInstanceOf(FileScript.class);
//    }
//
//    @Test
//    public void shouldReturnShellScript() {
//        Script script = new ScriptBuilder(new String[]{"joe%pass@localhost"}).convertToScript();
//        Assertions.assertThat(script).isInstanceOf(ShellOperationScript.class);
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void shouldContainJoinedOperationString() {
//        Script script = new ScriptBuilder(new String[]{"help", "login"}).convertToScript();
//        Object source = script.getSource();
//        Assertions.assertThat(source).isInstanceOf(List.class);
//        List<String> lines = (List<String>) source;
//        Assertions.assertThat(lines).contains("help login");
//    }
//}