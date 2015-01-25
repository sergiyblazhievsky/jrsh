package com.jaspersoft.cli.tool.command.common;

import com.beust.jcommander.internal.Maps;
import com.jaspersoft.cli.tool.command.AbstractCommand;
import org.testng.annotations.Test;

public class CommandProcessorTest {

    @Test(enabled = false)
    public void should_process_commands() {
        CommandProcessor processor = new CommandProcessor();
        processor.process(Maps.<String, AbstractCommand>newHashMap());
    }
}