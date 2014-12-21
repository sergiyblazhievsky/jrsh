package com.jaspersoft.cli.tool.command.common;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for {@link DefaultArgumentAppender}
 */
public class DefaultArgumentAppenderTest {

    @Test
    public void should_add_jrs_command_name_to_array() {
        String[] retrieved = DefaultArgumentAppender.append("command", "--key", "100500");
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved.length, 4);
        Assert.assertEquals(retrieved[0], "jrs");
    }

}