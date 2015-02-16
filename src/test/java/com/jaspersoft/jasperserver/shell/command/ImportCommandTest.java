package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportTaskRequestAdapter;
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
import java.io.OutputStream;
import java.io.PrintStream;

import static com.jaspersoft.jasperserver.shell.ExecutionMode.SHELL;
import static java.lang.System.setOut;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.shell.command.ImportCommand}
 */
@PrepareForTest({ImportCommand.class, SessionFactory.class, PrintStream.class, File.class})
public class ImportCommandTest extends PowerMockTestCase {

    @Spy
    private ImportCommand importCmd = new ImportCommand();

    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private JasperserverRestClient clientMock;
    @Mock
    private Session sessionMock;
    @Mock
    private File fileMock;
    @Mock
    private ImportService importServiceMock;
    @Mock
    private ExportService exportServiceMock;
    @Mock
    private ImportTaskRequestAdapter importRequestAdapterMock;
    @Mock
    private ExportRequestAdapter exportRequestAdapterMock;
    @Mock
    private OperationResult<StateDto> operationResultMock;
    @Mock
    private StateDto stateMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(enabled = true)
    @SuppressWarnings("unchecked")
    public void should_run_import_command() throws Exception {

        /** Given **/
        whenNew(File.class).withArguments(anyString()).thenReturn(fileMock);
        whenNew(RestClientConfiguration.class).withArguments("http://54.221.179.100/jasperserver-pro").thenReturn(configurationMock);
        whenNew(JasperserverRestClient.class).withArguments(configurationMock).thenReturn(clientMock);

        doReturn(sessionMock).when(clientMock).authenticate("superuser|organization_1", "superuser");
        doReturn(importServiceMock).when(sessionMock).importService();
        doReturn(exportServiceMock).when(sessionMock).exportService();
        doReturn(importRequestAdapterMock).when(importServiceMock).newTask();
        doReturn(exportRequestAdapterMock).when(exportServiceMock).task("fakeId");
        doReturn(operationResultMock).when(importRequestAdapterMock).create(fileMock);
        doReturn(operationResultMock).when(exportRequestAdapterMock).state();
        doReturn(stateMock).when(operationResultMock).getEntity();
        doReturn("fakeId").when(stateMock).getId();
        doReturn("finished").when(stateMock).getPhase();

        doReturn(false).when(fileMock).isDirectory();
        doReturn(true).when(fileMock).exists();

        SessionFactory.createSession("http://54.221.179.100/jasperserver-pro", "superuser", "superuser", "organization_1");
        importCmd.parameter("anonymous").getValues().add("/Users/alex/IdeaProjects/jrsh/src/main/resources/import.zip");
        importCmd.setMode(SHELL);

        PrintStream streamSpy = spy(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));
        setOut(streamSpy);


        /** When **/
        importCmd.run();


        /** Then **/
        verifyNew(RestClientConfiguration.class, times(1)).withArguments("http://54.221.179.100/jasperserver-pro");
        verifyNew(JasperserverRestClient.class, times(1)).withArguments(configurationMock);
        verifyNew(File.class, times(1)).withArguments("/Users/alex/IdeaProjects/jrsh/src/main/resources/import.zip");

        verify(fileMock, times(1)).isDirectory();
        verify(fileMock, times(1)).exists();

        verify(sessionMock, times(1)).importService();
        verify(sessionMock, times(1)).exportService();
        verify(importServiceMock, times(1)).newTask();

        verify(importRequestAdapterMock, never()).parameter(eq(ImportParameter.INCLUDE_AUDIT_EVENTS), eq(true));
        verify(importRequestAdapterMock, never()).parameter(eq(ImportParameter.SKIP_USER_UPDATE), eq(true));
        verify(importRequestAdapterMock, never()).parameter(eq(ImportParameter.UPDATE), eq(true));
        verify(importRequestAdapterMock, times(1)).create(fileMock);
        verify(operationResultMock, times(2)).getEntity();
    }

    @AfterMethod
    public void after() {
        reset(configurationMock, clientMock, sessionMock, fileMock, importServiceMock,
                exportServiceMock, importRequestAdapterMock, exportRequestAdapterMock,
                operationResultMock, stateMock);

    }
}