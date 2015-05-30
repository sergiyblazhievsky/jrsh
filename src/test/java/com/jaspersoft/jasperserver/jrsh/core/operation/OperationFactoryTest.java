package com.jaspersoft.jasperserver.jrsh.core.operation;//package com.jaspersoft.jasperserver.jrsh.core.operation;
//
//import com.jaspersoft.jasperserver.jrsh.core.operation.impl.ExportOperation;
//import com.jaspersoft.jasperserver.jrsh.core.operation.impl.HelpOperation;
//import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
//import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.yaml.snakeyaml.Yaml;
//
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import static org.mockito.Matchers.any;
//
///**
// * Unit tests for {@link OperationFactory}
// */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Yaml.class, OperationFactory.class})
//public class OperationFactoryTest {
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void shouldReturnProperOperationInstanceWhenPassOperationName() {
//        Operation loginOperation = OperationFactory.createOperationByName("login");
//        Assert.assertTrue(loginOperation instanceof LoginOperation);
//    }
//
//    @Test
//    public void shouldCreateInstanceOfOperationType() {
//        Operation testOperation = OperationFactory.createInstance(TestOperation.class);
//        Assert.assertTrue(testOperation instanceof TestOperation);
//    }
//
//    @Test
//    public void shouldReturnAllAvailableNonCustomOperation() {
//        Set<Operation> operations = OperationFactory.createOperationsByAvailableTypes();
//        Assert.assertEquals(3, operations.size());
//        Assert.assertTrue(operations.contains(new LoginOperation()));
//        Assert.assertTrue(operations.contains(new ExportOperation()));
//        Assert.assertTrue(operations.contains(new HelpOperation()));
//    }
//
//    @Test
//    public void shouldReturnAllAvailableNonCustomOperationTypes() {
//        Set<Class<? extends Operation>> operationTypes = OperationFactory.getOperationTypes();
//        Assert.assertEquals(3, operationTypes.size());
//        Assert.assertTrue(operationTypes.contains(LoginOperation.class));
//        Assert.assertTrue(operationTypes.contains(ExportOperation.class));
//        Assert.assertTrue(operationTypes.contains(HelpOperation.class));
//    }
//
//    @Test
//    public void shouldThrowAnExceptionWhenTryToCreateInstanceOfAbstractOperation() {
//        thrown.expect(CouldNotCreateOperationInstance.class);
//        OperationFactory.createInstance(TestAbstractOperation.class);
//    }
//
//    @Test
//    public void shouldThrowAnExceptionWhenTryToGetOperationUsingWrongOperationName() {
//        thrown.expect(NoOperationFoundException.class);
//        OperationFactory.createOperationByName("wrong_operation_name");
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void shouldReadConfigFileAndReturnConfigMap() throws Exception {
//        /* Given */
//        Map<String, List<String>> dummyConfig = new HashMap<String, List<String>>() {{
//            put("packages-to-scan", Arrays.<String>asList("com.test.action.*", "org.app.operation.*"));
//        }};
//
//        Yaml yamlMock = PowerMockito.mock(Yaml.class);
//        PowerMockito.whenNew(Yaml.class).withNoArguments().thenReturn(yamlMock);
//        PowerMockito.doReturn(dummyConfig).when(yamlMock).load(any(InputStream.class));
//
//        /* When */
//        Map<String, Object> configMap = OperationFactory.getConfig();
//
//        /* Then */
//        Assert.assertNotNull(configMap);
//        Assert.assertTrue(((List<String>) configMap.get("packages-to-scan")).contains("com.test.action.*"));
//        Assert.assertTrue(((List<String>) configMap.get("packages-to-scan")).contains("org.app.operation.*"));
//    }
//}