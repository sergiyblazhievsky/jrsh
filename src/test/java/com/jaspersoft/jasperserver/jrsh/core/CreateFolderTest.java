package com.jaspersoft.jasperserver.jrsh.core;

import com.jaspersoft.jasperserver.jrsh.core.common.DirectoryDoesNotExistException;
import com.jaspersoft.jasperserver.jrsh.core.common.ZipUtil;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

public class CreateFolderTest {

    public static final String FOLDER = "/Users/akrasnyanskiy/IdeaProjects/jrsh/src/main/resources/test1";
    public static final String WRONG_FOLDER = "/Users/akrasnyanskiy/IdeaProjects/jrsh/src/main/resources/wrong_dir";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPackFolderContentIntoAZipFileAndDeleteIt() {
        File importFile = ZipUtil.pack(FOLDER);
        Assert.assertNotNull(importFile);
        Assert.assertTrue(importFile.exists());
        boolean isDeleted = importFile.delete();
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void shouldThrowAnExceptionWhenPathIsNotADirectory() {
        thrown.expect(DirectoryDoesNotExistException.class);
        thrown.expectMessage(String.format("Directory [%s] does not exist.", WRONG_FOLDER));
        ZipUtil.pack(WRONG_FOLDER);
    }
}
