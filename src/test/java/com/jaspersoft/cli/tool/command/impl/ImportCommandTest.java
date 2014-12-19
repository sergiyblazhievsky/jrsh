package com.jaspersoft.cli.tool.command.impl;

import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportTaskRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link ImportCommand}
 */
@PrepareForTest({SessionFactory.class, ImportCommand.class, File.class})
public class ImportCommandTest extends PowerMockTestCase {

    @Mock
    private Session sessionMock;
    @Mock
    private ImportService importServiceMock;
    @Mock
    private ExportService exportServiceMock;
    @Mock
    private ImportTaskRequestAdapter importTaskRequestAdapterMock;
    @Mock
    private ExportRequestAdapter exportRequestAdapterMock;
    @Mock
    private OperationResult<StateDto> operationResultMockForImport;
    @Mock
    private OperationResult<StateDto> operationResultMockForExport;
    @Mock
    private StateDto stateMock;
    @Mock
    private StateDto stateMockForExportTask;

    private ImportCommand importCmd;

    @BeforeMethod
    public void before() {
        initMocks(this);
        importCmd = new ImportCommand("import", 2);
    }

    @Test
    public void should_set_right_level_of_command() {
        Assert.assertSame(importCmd.getLevel(), 2);
    }

    @Test
    public void should_set_correct_command_name() {
        Assert.assertEquals(importCmd.getCommandName(), "import");
    }

    @Test
    public void should_import_resources_as_zip_file() throws Exception {

        // given
        PowerMockito.mockStatic(SessionFactory.class);
        PowerMockito.when(SessionFactory.getInstance()).thenReturn(sessionMock);

        File fileMock = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withArguments(anyString()).thenReturn(fileMock);

        PowerMockito.doReturn(importServiceMock).when(sessionMock).importService();
        PowerMockito.doReturn(exportServiceMock).when(sessionMock).exportService();
        PowerMockito.doReturn(importTaskRequestAdapterMock).when(importServiceMock).newTask();
        PowerMockito.doReturn(exportRequestAdapterMock).when(exportServiceMock).task("2f097a8a-7200-42ea-a9d8-d1762d3c7861");
        PowerMockito.doReturn(operationResultMockForImport).when(importTaskRequestAdapterMock).create(fileMock);
        PowerMockito.doReturn(operationResultMockForExport).when(exportRequestAdapterMock).state();
        PowerMockito.doReturn(stateMock).when(operationResultMockForImport).entity();
        PowerMockito.doReturn(stateMockForExportTask).when(operationResultMockForExport).entity();
        PowerMockito.doReturn("inprogress").doReturn("inprogress").doReturn("finished").when(stateMockForExportTask).getPhase();
        PowerMockito.doReturn("2f097a8a-7200-42ea-a9d8-d1762d3c7861").when(stateMock).getId();

        // when
        importCmd.setFile("/Users/alexkrasnyanskiy/IdeaProjects/jrs-command-line-tool/src/main/resources/import.zip");
        Void retrieved = importCmd.execute();

        // than
        Assert.assertNull(retrieved);
        Assert.assertEquals(importCmd.getFile(), "/Users/alexkrasnyanskiy/IdeaProjects/jrs-command-line-tool/src/main/resources/import.zip");

        PowerMockito.verifyStatic(times(4));
        SessionFactory.getInstance();

        InOrder inOrder = inOrder(sessionMock, importServiceMock, importTaskRequestAdapterMock, operationResultMockForImport);
        inOrder.verify(sessionMock, times(1)).importService();
        inOrder.verify(importServiceMock, times(1)).newTask();
        inOrder.verify(importTaskRequestAdapterMock, times(1)).create(fileMock);
        inOrder.verify(operationResultMockForImport, times(1)).entity();

        Mockito.verify(sessionMock, times(3)).exportService();
        Mockito.verify(exportServiceMock, times(3)).task("2f097a8a-7200-42ea-a9d8-d1762d3c7861");
        Mockito.verify(exportRequestAdapterMock, times(3)).state();
        Mockito.verify(stateMockForExportTask, times(3)).getPhase();
    }

    @Test(enabled = false)
    public void should_return_null_state() throws Exception {

        // given
        PowerMockito.mockStatic(SessionFactory.class);
        PowerMockito.when(SessionFactory.getInstance()).thenReturn(sessionMock);

        File fileMock = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withArguments(anyString()).thenReturn(fileMock);

        PowerMockito.doReturn(importServiceMock).when(sessionMock).importService();
        PowerMockito.doReturn(importTaskRequestAdapterMock).when(importServiceMock).newTask();
        PowerMockito.doReturn(operationResultMockForImport).when(importTaskRequestAdapterMock).create(fileMock);
        PowerMockito.doReturn(null).when(operationResultMockForImport).entity();

        importCmd.execute();
    }

    @AfterMethod
    public void after() {
        reset(sessionMock, exportServiceMock, importServiceMock, importTaskRequestAdapterMock,
                operationResultMockForImport, stateMock, exportRequestAdapterMock,
                operationResultMockForExport, stateMockForExportTask);
        importCmd = null;
    }
}