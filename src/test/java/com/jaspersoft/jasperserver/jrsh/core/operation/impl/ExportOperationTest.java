package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

import static org.joda.time.DateTimeZone.UTC;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.easymock.PowerMock.*;

/**
 * Created by serhii.blazhyievskyi on 8/21/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({File.class, ExportOperation.class})
public class ExportOperationTest {

    @Mock
    private File fileMock;

    @Spy
    private ExportOperation exportOperation  = new ExportOperation();;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldExecuteOperationWithoutExceptionForWindows() throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            // Given
            InputStream stubInputStream = IOUtils.toInputStream("some test data for my input stream");

            // When
            exportOperation.writeToFile(stubInputStream);
            String fileUri = Whitebox.getInternalState(exportOperation, "fileUri");

            // Then
            assertNotNull(fileUri);
            assertTrue(new File(fileUri).exists());
        }
    }

    @After
    public void after() {
        Mockito.reset(fileMock);

    }
}
