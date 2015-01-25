package com.jaspersoft.jasperserver.shell.command;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@PrepareForTest({ImportCommand.class})
public class ImportCommandTest extends PowerMockTestCase {

    private ImportCommand importCmd;

    @BeforeMethod
    public void before() {
        importCmd = new ImportCommand();
    }

    @Test(enabled = false)
    public void should_do_some_action() {
        importCmd.run();
    }

    @AfterMethod
    public void after() {
        importCmd = null;
    }
}