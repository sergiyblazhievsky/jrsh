//package com.jaspersoft.cli.tool.command.common;
//
//import com.beust.jcommander.JCommander;
//import com.jaspersoft.cli.tool.command.impl.JrsCommand;
//import org.apache.log4j.Logger;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.times;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//@PrepareForTest({CommandBuilder.class,Logger.class, System.class, JCommanderContext.class})
//public class CommandBuilderTest extends PowerMockTestCase {
//
//    @Mock
//    private Logger logMock;
//
//    private JCommander spy;
//    private final String[] ARGS = {
//            "-s", "http://54.90.191.114/jasperserver-pro",
//            "-u", "superuser",
//            "-p", "superuser",
//            "show", "server-info"};
//    private final String[] WRONG_ARGS = {"no-command"};
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//        spy = Mockito.spy(new JCommander());
//    }
//
//    @Test(enabled = false)
//    public void should_exit() throws Exception {
//
//        PowerMockito.mockStatic(Logger.class, JCommanderContext.class, System.class);
//        PowerMockito.when(Logger.getLogger(CommandBuilder.class)).thenReturn(logMock);
//        PowerMockito.when(JCommanderContext.getInstance()).thenReturn(spy);
//        PowerMockito.when(System.class, "exit", 1).thenAnswer(new Answer<Object>() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                Assert.assertSame(invocation.getArguments(), 1);
//                return null;
//            }
//        });
//
//        new CommandBuilder(WRONG_ARGS);
//
//        Mockito.verify(spy, times(5)).usage();
//
//    }
//
//    @Test
//    public void should_init_jcommander() {
//
//        PowerMockito.mockStatic(JCommanderContext.class);
//        PowerMockito.when(JCommanderContext.getInstance()).thenReturn(spy);
//
//        CommandBuilder builder = new CommandBuilder(ARGS);
//
//        Assert.assertNotNull(builder);
//        Mockito.verify(spy, times(1)).getCommands();
//        Mockito.verify(spy, times(1)).addCommand(eq("jrs"), any(JrsCommand.class));
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(spy, logMock);
//    }
//}