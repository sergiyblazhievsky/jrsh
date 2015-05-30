package com.jaspersoft.jasperserver.jrsh.core.common;

import jline.console.ConsoleReader;
import org.junit.Assert;
import org.junit.Test;

public class ConsoleBuilderTest {

    @Test
    public void shouldBuildConsoleWithPrompt() {
        ConsoleReader console = new ConsoleBuilder().withPrompt("$> ").build();
        Assert.assertEquals("$> ", console.getPrompt());
    }
}