package com.jaspersoft.cli.tool.core.v2.context;

import com.jaspersoft.cli.tool.core.v2.command.AbstractCommand;
import com.jaspersoft.cli.tool.core.v2.command.Command;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.jaspersoft.cli.tool.core.v2.command.factory.CommandFactory.create;
import static javax.xml.stream.XMLInputFactory.newFactory;
import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

/**
 * Central class to provide configuration for an CLI Tool.
 *
 * @author A. Krasnyanskiy
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class ClassPathXmlCliToolConfigurationContext implements CliToolContext {

    /**
     * Commands DI container.
     */
    private Map<String, Command> dependencies = new HashMap<>();

    /**
     * Initializing of context.
     *
     * @param path path to context metadata (components description)
     * @throws IOException
     * @throws XMLStreamException
     * @throws ClassNotFoundException
     */
    public ClassPathXmlCliToolConfigurationContext(String path) throws IOException, XMLStreamException, ClassNotFoundException {
        InputStream resource = ClassPathXmlCliToolConfigurationContext.class.getResourceAsStream(path);
        XMLInputFactory factory = newFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(resource);
        while (reader.hasNext()) {
            reader.next();
            int eventType = reader.getEventType();
            switch (eventType) {
                case START_ELEMENT:
                    startElement(reader);
                    break;
                case END_ELEMENT:
                    //ended(reader.getLocalName());
                    break;
            }
        }
    }

    void startElement(XMLStreamReader reader) throws ClassNotFoundException {
        String commandName = "";
        AbstractCommand command = null;
        for (int idx = 0; idx < reader.getAttributeCount(); ++idx) {
            if (reader.getAttributeLocalName(idx).equals("class")) {
                Class<? extends AbstractCommand> commandClass = (Class<? extends AbstractCommand>) Class.forName(reader.getAttributeValue(idx));
                command = create(commandClass, null, null);
                dependencies.put(commandName, command);
            }
            if (reader.getAttributeLocalName(idx).equals("name")) {
                commandName = reader.getAttributeValue(idx);
                if (command != null) {
                    command.setCommandName(commandName);
                }
            }
        }
    }

    @Override
    public Command getCommand(String name) {
        return dependencies.get(name);
    }

    @Override
    public <T extends Command> T getCommand(Class<T> commandClass) {
        for (Command command : dependencies.values()) {
            if (command.getClass().equals(commandClass)) {
                return (T) command;
            }
        }
        return null;
    }
}
