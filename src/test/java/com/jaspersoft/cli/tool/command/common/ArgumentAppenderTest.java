package com.jaspersoft.cli.tool.command.common;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for {@link ArgumentAppender}
 */
public class ArgumentAppenderTest {

    @Test
    public void should_add_jrs_command_name_to_array() {
        String[] retrieved = ArgumentAppender.append("command", "--key", "100500");
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved.length, 4);
        Assert.assertEquals(retrieved[0], "jrs");
    }

}