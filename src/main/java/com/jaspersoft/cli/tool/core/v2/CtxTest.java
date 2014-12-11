package com.jaspersoft.cli.tool.core.v2;

import com.jaspersoft.cli.tool.core.v2.command.Command;
import com.jaspersoft.cli.tool.core.v2.command.impl.HelpCommand;
import com.jaspersoft.cli.tool.core.v2.context.ClassPathXmlCliToolConfigurationContext;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * @author A. Krasnyanskiy
 */
public class CtxTest {
    public static void main(String[] args) throws IOException, XMLStreamException, ClassNotFoundException {
        ClassPathXmlCliToolConfigurationContext ctx = new ClassPathXmlCliToolConfigurationContext("/cmdMetaData.xml");
        //Command command = ctx.getCommand("export");
        Command command = ctx.getCommand(HelpCommand.class);
        command.execute();
    }
}
