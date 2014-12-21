package com.jaspersoft.cli.tool.command.impl;

import com.jaspersoft.cli.tool.command.common.tree.TreeConverter;
import com.jaspersoft.cli.tool.command.common.tree.TreeNode;
import com.jaspersoft.cli.tool.command.factory.SessionFactory;
import com.jaspersoft.cli.tool.exception.WrongPathException;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceListWrapper;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesService;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.FOLDER_URI;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link RepoShowCommand}
 */
@PrepareForTest({SessionFactory.class, RepoShowCommand.class, TreeConverter.class})
public class RepoShowCommandTest extends PowerMockTestCase {

    @Mock
    private Session sessionMock;
    @Mock
    private OperationResult<ClientResourceListWrapper> resultMock;
    @Mock
    private ClientResourceListWrapper listWrapperMock;
    @Mock
    private TreeConverter converterMock;
    @Mock
    private TreeNode node;
    @Mock
    private BatchResourcesAdapter resourcesAdapterMock;
    @Mock
    private ResourcesService resourcesServiceMock;

    private RepoShowCommand showResourcesCmd;

    @BeforeMethod
    public void before() {
        initMocks(this);
        showResourcesCmd = new RepoShowCommand("resources", 3);
    }

    @Test(enabled = false)
    public void should_print_tree_and_return_null_as_result() throws Exception {

        // given
        List<ClientResourceLookup> lookupList = new ArrayList<>(asList(
                new ClientResourceLookup().setUri("/public/test/old_folder"),
                new ClientResourceLookup().setUri("/public/test/new_folder")));

        PowerMockito.whenNew(TreeConverter.class).withNoArguments().thenReturn(converterMock);
        PowerMockito.doReturn(node).when(converterMock).toTree(any(List.class), anyString());

        PowerMockito.mockStatic(SessionFactory.class);
        PowerMockito.when(SessionFactory.getInstance()).thenReturn(sessionMock);

        PowerMockito.doReturn(resourcesServiceMock).when(sessionMock).resourcesService();
        PowerMockito.doReturn(resourcesAdapterMock).when(resourcesServiceMock).resources();
        PowerMockito.doReturn(resourcesAdapterMock).when(resourcesAdapterMock).parameter(FOLDER_URI, "/");
        PowerMockito.doReturn(resultMock).when(resourcesAdapterMock).search();
        PowerMockito.doReturn(listWrapperMock).when(resultMock).entity();
        PowerMockito.doReturn(lookupList).when(listWrapperMock).getResourceLookups();

        // when
        Void retrieved = showResourcesCmd.execute();

        // than
        Assert.assertNull(retrieved);

        PowerMockito.verifyStatic();
        SessionFactory.getInstance();

        PowerMockito.verifyNew(TreeConverter.class, times(1)).withNoArguments();

        Mockito.verify(sessionMock, times(1)).resourcesService();
        Mockito.verify(resourcesServiceMock, times(1)).resources();
        Mockito.verify(resourcesAdapterMock, times(1)).parameter(FOLDER_URI, "/");
        Mockito.verify(resourcesAdapterMock, times(1)).search();
        Mockito.verify(resultMock, times(1)).entity();
        Mockito.verify(listWrapperMock, times(1)).getResourceLookups();
        Mockito.verify(converterMock, times(1)).toTree(anyList(), anyString());
        Mockito.verify(node, times(1)).print();
    }

    @Test(expectedExceptions = WrongPathException.class)
    public void should_throw_an_exception_when_setted_wrong_resource_path() {
        // given
        PowerMockito.mockStatic(SessionFactory.class);
        PowerMockito.when(SessionFactory.getInstance()).thenReturn(sessionMock);

        PowerMockito.doReturn(resourcesServiceMock).when(sessionMock).resourcesService();
        PowerMockito.doReturn(resourcesAdapterMock).when(resourcesServiceMock).resources();
        PowerMockito.doReturn(resourcesAdapterMock).when(resourcesAdapterMock).parameter(FOLDER_URI, "/wrong/path");
        PowerMockito.doThrow(new ResourceNotFoundException()).when(resourcesAdapterMock).search();

        // when
        showResourcesCmd.setPath("/wrong/path");
        showResourcesCmd.execute();

        // than
        // see annotation parameter (expectedExceptions)
    }

    @Test(description = "path being setted by JCommander library using setter or Reflection API, not manually")
    public void should_set_path() {
        showResourcesCmd.setPath("/my/path");
        String retrieved = showResourcesCmd.getPath();
        Assert.assertEquals(retrieved, "/my/path");
        Assert.assertNotEquals(retrieved, "/wrong/path");
    }

    @Test
    public void should_set_right_level_of_command() {
        Assert.assertSame(showResourcesCmd.getLevel(), 3);
    }

    @Test
    public void should_set_correct_command_name() {
        Assert.assertEquals(showResourcesCmd.getCommandName(), "resources");
    }

    @AfterMethod
    public void after() {
        reset(sessionMock, resultMock, listWrapperMock, converterMock, node, resourcesServiceMock, resourcesAdapterMock);
        showResourcesCmd = null;
    }
}