package com.jaspersoft.jasperserver.shell.command;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.OutputStream;
import java.io.PrintStream;

import static java.lang.System.setOut;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.support.membermodification.MemberMatcher.everythingDeclaredIn;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.shell.command.ProfileCommand}
 */
@PrepareForTest({ProfileCommand.class, PrintStream.class})
public class ProfileCommandTest extends PowerMockTestCase {

    private ProfileCommand profile;

    @BeforeMethod
    public void before() {
        profile = spy(new ProfileCommand());
        Command.profile.setUrl(null);
        Command.profile.setUsername(null);
    }


    @Test(invocationCount = 2, enabled = true)
    public void should_invoke_run_method() {

        /** Given **/
        suppress(everythingDeclaredIn(ProfileCommand.class));

        /** When **/
        profile.run();

        /** Then **/
        verify(profile, times(1)).run();
    }


    @Test(invocationCount = 2, enabled = true)
    public void should_invoke_println_method_with_proper_param() {

        /** Given **/
        PrintStream spy = spy(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));
        setOut(spy);

        /** When **/
        profile.run();

        /** Then **/
        verify(spy, times(1)).println("Not available.");
    }


    @Test(invocationCount = 2, enabled = true)
    public void should_invoke_println_method_when_profile_not_empty() {

        /** Given **/
        PrintStream spy = spy(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));
        setOut(spy);
        Command.profile.setUrl("http://localhost:4444/jasperserver-pro");
        Command.profile.setUsername("superuser");

        /** When **/
        profile.run();

        /** Then **/
        verify(spy, times(1)).printf(anyString(), anyVararg());
    }

    @AfterMethod
    public void after() {
        profile = null;
    }
}