//package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;
//
//import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ScriptEvaluationStrategy;
//import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ShellEvaluationStrategy;
//import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ToolEvaluationStrategy;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameters;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//@RunWith(Parameterized.class)
//public class EvaluationStrategyTypeIdentifierTest {
//
//    @Parameters
//    public static Collection<Object[]> data() {
//        return Arrays.asList(new Object[][]{
//                {new String[]{}, ToolEvaluationStrategy.class},
//                {new String[]{"help"}, ToolEvaluationStrategy.class},
//                {new String[]{"help", "export"}, ToolEvaluationStrategy.class},
//                {new String[]{"--script", "/Users/sasha.botsman/magneto.jrs"}, ScriptEvaluationStrategy.class},
//                {new String[]{"--script", "magneto.jrs"}, ScriptEvaluationStrategy.class},
//                {new String[]{""}, ToolEvaluationStrategy.class},
//                {new String[]{"user%password@localhost:8080/jasperserver-pro"}, ShellEvaluationStrategy.class},
//                {new String[]{"user|org%password@localhost:8080/jasperserver-pro", "export", "repository", "/public"}, ToolEvaluationStrategy.class}
//        });
//    }
//
//    private String[] parameters;
//    private Class<? extends EvaluationStrategy> expectedType;
//
//    public EvaluationStrategyTypeIdentifierTest(String[] parameters, Class<? extends EvaluationStrategy> expectedType) {
//        this.parameters = parameters;
//        this.expectedType = expectedType;
//    }
//
//    @Test
//    public void shouldIdentifyEvaluationStrategyTypeBasedOnApplicationParameters() {
//        Class<? extends EvaluationStrategy> retrievedType = EvaluationStrategyTypeIdentifier.identifyType(parameters);
//        Assert.assertEquals(expectedType, retrievedType);
//    }
//}