//package com.jaspersoft.jasperserver.shell.command;
//
//import com.jaspersoft.jasperserver.shell.command.impl.SessionCommand;
//import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.lang.reflect.Field;
//
//import static java.lang.System.setOut;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.anyVararg;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.powermock.api.support.membermodification.MemberMatcher.field;
//
//
///**
// * Unit tests for {@link com.jaspersoft.jasperserver.shell.command.impl.SessionCommand}
// */
//@Test
//@PrepareForTest({SessionCommand.class, PrintStream.class})
//public class SessionCommandTest extends PowerMockTestCase {
//
//    private SessionCommand session;
//
//    @BeforeMethod
//    public void before() {
//        session = spy(new SessionCommand());
//        Command.profile.setUrl(null);
//        Command.profile.setUsername(null);
//    }
//
//    public void should_invoke_print_method_with_proper_param_when_profile_is_empty() {
//
//        /** Given **/
//        PrintStream spy = spy(new PrintStream(new OutputStream() {
//            public void write(int b) {}
//        }));
//        setOut(spy);
//
//        /** When **/
//        session.run();
//
//        /** Then **/
//        verify(spy, times(1)).println("There's no active session.");
//    }
//
//    public void should_invoke_print_method_with_proper_param_when_profile_is_not_empty() throws IllegalAccessException {
//
//        /** Given **/
//        PrintStream spy = spy(new PrintStream(new OutputStream() {
//            public void write(int b) {
//            }
//        }));
//        setOut(spy);
//        Field field = field(SessionFactory.class, "sessionStartTime");
//        field.set(SessionFactory.class, 1234764532434365L);
//        Command.profile.setUrl("http://localhost:4444/jasperserver-pro");
//        Command.profile.setUsername("superuser");
//
//        /** When **/
//        session.run();
//
//        /** Then **/
//        verify(spy, times(1)).printf(anyString(), anyVararg());
//    }
//
//    @AfterMethod
//    public void after() {
//        session = null;
//    }
//}