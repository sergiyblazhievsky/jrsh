package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static com.jaspersoft.jasperserver.shell.ExecutionMode.SHELL;
import static java.lang.System.setOut;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Unit test for {@link ExportCommand}
 */
@Test(enabled = false)
@SuppressWarnings("unchecked")
@PrepareForTest({ExportCommand.class, SessionFactory.class, FileOutputStream.class, PrintStream.class, File.class})
public class ExportCommandTest extends PowerMockTestCase {

    @Spy
    private ExportCommand export = new ExportCommand();

    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private JasperserverRestClient clientMock;
    @Mock
    private Session sessionMock;
    @Mock
    private File fileMock;
    @Mock
    private InputStream streamMock;
//    @Mock
//    private FileOutputStream fileOutputStreamMock;
    @Mock
    private ImportService importServiceMock;
    @Mock
    private ExportService exportServiceMock;
    @Mock
    private ExportTaskAdapter exportTaskAdapterMock;
    @Mock
    private ExportRequestAdapter exportRequestAdapterMock;
    @Mock
    private OperationResult<StateDto> operationResultMock;
    @Mock
    private OperationResult<InputStream> inputStreamOperationResultMock;
    @Mock
    private StateDto stateMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    public void should_execute_export_command() throws Exception {

        /** Given **/

        whenNew(File.class).withArguments(anyString()).thenReturn(fileMock);
        //whenNew(RestClientConfiguration.class).withArguments("http://54.221.179.100/jasperserver-pro").thenReturn(configurationMock);
        //whenNew(JasperserverRestClient.class).withArguments(configurationMock).thenReturn(clientMock);
        //whenNew(FileOutputStream.class).withArguments(anyString()).thenReturn(fileOutputStreamMock);

        mockStatic(SessionFactory.class);
        when(SessionFactory.createSession("http://54.221.179.1/jasperserver-pro", "superuser", "superuser", "organization_1"));

        doReturn(sessionMock).when(clientMock).authenticate("superuser|organization_1", "superuser");

        doReturn(exportServiceMock).when(sessionMock).exportService();
        doReturn(exportRequestAdapterMock).when(exportServiceMock).task(anyString());
        doReturn(inputStreamOperationResultMock).when(exportRequestAdapterMock).fetch();
        doReturn(streamMock).when(inputStreamOperationResultMock).getEntity();

        doReturn(exportTaskAdapterMock).when(exportServiceMock).newTask();
        doReturn(exportTaskAdapterMock).when(exportTaskAdapterMock).parameters(anyList());
        doReturn(operationResultMock).when(exportTaskAdapterMock).create();
        doReturn(exportTaskAdapterMock).when(exportTaskAdapterMock).uri("/public/Samples/Reports/06g.ProfitDetailReport");

        doReturn(stateMock).when(operationResultMock).getEntity();
        doReturn("fakeId").when(stateMock).getId();
        doReturn("finished").when(stateMock).getPhase();

        doReturn(false).when(fileMock).isDirectory();
        doReturn(true).when(fileMock).exists();

        SessionFactory.createSession("http://54.221.179.1/jasperserver-pro", "superuser", "superuser", "organization_1");
        export.parameter("anonymous").getValues().add("/public/Samples/Reports/06g.ProfitDetailReport");
        export.setMode(SHELL);

        PrintStream streamSpy = spy(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));
        setOut(streamSpy);

        /** When **/
        export.run();
    }

    @AfterMethod
    public void after() {
        reset(configurationMock, clientMock, sessionMock, fileMock, importServiceMock,
                exportServiceMock, exportTaskAdapterMock, exportRequestAdapterMock,
                operationResultMock, stateMock, inputStreamOperationResultMock, streamMock
                /*fileOutputStreamMock*/);
    }
}