package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

@RunWith(PowerMockRunner.class)
@PrepareForTest({File.class, ImportOperation.class})
public class ImportOperationTest {

    @Mock
    private File fileMock;
    private ImportOperation importOperation;

    @Before
    public void before() {
        importOperation = new ImportOperation();
        MockitoAnnotations.initMocks(fileMock);
    }

    @Test
    public void shouldExecuteOperationAndReplaceTildeWithUserHomePathOnUnixOS() throws Exception {
        if (!SystemUtils.IS_OS_WINDOWS) {
            // Given
            final String PATH = "~/Downloads/jrsh/file.zip";
            PowerMockito.whenNew(File.class).withArguments(PATH).thenReturn(fileMock);
            importOperation.setPath(PATH);

            // Let's skip those checks to let method
            // ignore unimportant for this test logic
            PowerMockito.doReturn(false).when(fileMock).isDirectory();
            PowerMockito.doReturn(false).when(fileMock).isFile();

            // When
            importOperation.execute(null);

            // Then
            String fullPath = PATH.replaceFirst("^~", System.getProperty("user.home"));
            PowerMockito.verifyNew(File.class, Mockito.times(1)).withArguments(fullPath);
        }
    }

    @Test
    public void shouldExecuteOperationWithoutReplacingTildeInPath() throws Exception {
        if (!SystemUtils.IS_OS_WINDOWS) {
            // Given
            final String PATH = "/Users/alex/Downloads/jrsh/file.zip";
            PowerMockito.whenNew(File.class).withArguments(PATH).thenReturn(fileMock);
            importOperation.setPath(PATH);

            // Let's skip those checks to let method
            // ignore unimportant for this test logic
            PowerMockito.doReturn(false).when(fileMock).isDirectory();
            PowerMockito.doReturn(false).when(fileMock).isFile();

            // When
            importOperation.execute(null);

            // Then
            PowerMockito.verifyNew(File.class, Mockito.times(1)).withArguments(PATH);
        }
    }

    @After
    public void after() {
        Mockito.reset(fileMock);
    }
}