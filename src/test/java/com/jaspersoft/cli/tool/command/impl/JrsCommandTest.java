package com.jaspersoft.cli.tool.command.impl;

import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link JrsCommand}
 */
@PrepareForTest({SessionFactory.class})
public class JrsCommandTest extends PowerMockTestCase {

    @Mock
    private Session sessionMock;

    private JrsCommand jrsCmd;

    @BeforeMethod
    public void before() {
        initMocks(this);
        jrsCmd = new JrsCommand("jrs", 1);
    }

    @Test
    public void should_set_right_level_of_command() {
        Assert.assertSame(jrsCmd.getLevel(), 1);
    }

    @Test
    public void should_set_correct_command_name() {
        Assert.assertEquals(jrsCmd.getCommandName(), "jrs");
    }

    @Test
    public void should_create_session_via_factory() {

        // given
        jrsCmd.setUrl("http://54.221.48.79/jasperserver-pro");
        jrsCmd.setUsername("superuser");
        jrsCmd.setPassword("superuser");
        jrsCmd.setDebug(true);

        PowerMockito.mockStatic(SessionFactory.class);
        PowerMockito.when(SessionFactory.create("http://54.221.48.79/jasperserver-pro", "superuser", "superuser"))
                .thenReturn(sessionMock);

        // when
        jrsCmd.execute();

        // than
        PowerMockito.verifyStatic(times(1));
        SessionFactory.create("http://54.221.48.79/jasperserver-pro", "superuser", "superuser");

        Assert.assertEquals(jrsCmd.getPassword(), "superuser");
        Assert.assertEquals(jrsCmd.getUsername(), "superuser");
        Assert.assertEquals(jrsCmd.getUrl(), "http://54.221.48.79/jasperserver-pro");
        Assert.assertTrue(jrsCmd.isDebug());
    }

    @AfterMethod
    public void after() {
        reset(sessionMock);
        jrsCmd = null;
    }
}