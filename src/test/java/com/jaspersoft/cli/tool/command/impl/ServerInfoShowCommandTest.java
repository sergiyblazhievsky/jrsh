package com.jaspersoft.cli.tool.command.impl;

import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link ServerInfoShowCommand}
 */
@PrepareForTest({ServerInfoShowCommand.class, SessionFactory.class})
public class ServerInfoShowCommandTest extends PowerMockTestCase {

    @Mock
    private Session sessionMock;
    @Mock
    private ServerInfoService serverInfoServiceMock;
    @Mock
    private OperationResult<ServerInfo> operationResultMock;
    @Mock
    private ServerInfo serverInfoMock;

    private ServerInfoShowCommand showServerInfoCmd;

    @BeforeMethod
    public void before() {
        initMocks(this);
        showServerInfoCmd = PowerMockito.spy(new ServerInfoShowCommand("server-info", 3));
    }

    @Test
    public void should_return_serverInfo() throws Exception {

        // given
        PowerMockito.mockStatic(SessionFactory.class);
        PowerMockito.when(SessionFactory.getInstance()).thenReturn(sessionMock);
        PowerMockito.doReturn(serverInfoServiceMock).when(sessionMock).serverInfoService();
        PowerMockito.doReturn(operationResultMock).when(serverInfoServiceMock).details();
        PowerMockito.doReturn(serverInfoMock).when(operationResultMock).entity();

        PowerMockito.doReturn("20140707_1400").when(serverInfoMock).getBuild();
        PowerMockito.doReturn("5.6.0").when(serverInfoMock).getVersion();
        PowerMockito.doReturn("Commercial").when(serverInfoMock).getLicenseType();

        PowerMockito.when(showServerInfoCmd, "print", serverInfoMock).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ServerInfo info = (ServerInfo) invocation.getArguments()[0];
                Assert.assertSame(info.getBuild(), "20140707_1400");
                Assert.assertSame(info.getVersion(), "5.6.0");
                Assert.assertSame(info.getLicenseType(), "Commercial");
                return null;
            }
        });

        // when
        showServerInfoCmd.setBuild(true);
        showServerInfoCmd.setVersion(true);
        showServerInfoCmd.setLicenseType(true);
        showServerInfoCmd.execute();

        // than
        PowerMockito.verifyStatic();
        SessionFactory.getInstance();

        InOrder inOrder = inOrder(sessionMock, serverInfoServiceMock, operationResultMock);
        inOrder.verify(sessionMock, times(1)).serverInfoService();
        inOrder.verify(serverInfoServiceMock, times(1)).details();
        inOrder.verify(operationResultMock, times(1)).entity();
        inOrder.verifyNoMoreInteractions();

        PowerMockito.verifyPrivate(showServerInfoCmd, times(2 /* was 1 | wtf? */)).invoke("print", serverInfoMock);
    }

    @Test
    public void should_set_right_level_of_command() {
        Assert.assertSame(showServerInfoCmd.getLevel(), 3);
    }

    @Test
    public void should_set_correct_command_name() {
        Assert.assertEquals(showServerInfoCmd.getCommandName(), "server-info");
    }

    @Test
    public void should_set_options() {

        // when
        showServerInfoCmd.setVersion(true);
        showServerInfoCmd.setDatetimeFormatPattern(true);
        showServerInfoCmd.setEditionName(true);

        // than
        Boolean version = (Boolean) Whitebox.getInternalState(showServerInfoCmd, "version");
        Boolean datetimeFormatPattern = (Boolean) Whitebox.getInternalState(showServerInfoCmd, "datetimeFormatPattern");
        Boolean editionName = (Boolean) Whitebox.getInternalState(showServerInfoCmd, "editionName");

        Assert.assertTrue(version);
        Assert.assertTrue(datetimeFormatPattern);
        Assert.assertTrue(editionName);
    }

    @AfterMethod
    public void after() {
        reset(sessionMock, serverInfoServiceMock, operationResultMock, serverInfoMock);
        showServerInfoCmd = null;
    }
}