package com.jaspersoft.cli.tool.command.common;

import com.beust.jcommander.IStringConverter;
import com.jaspersoft.cli.tool.command.AbstractCommand.OutputFormat;
import com.jaspersoft.cli.tool.exception.UnknownFormatOptionException;

import static com.jaspersoft.cli.tool.command.AbstractCommand.OutputFormat.*;

/**
* @author Alex Krasnyanskiy
*/
public class OptionConverter implements IStringConverter<OutputFormat> {
    @Override
    public OutputFormat convert(String value) {
        switch (value) {
            case "json": return JSON;
            case "text": return TEXT;
            case "list": return LIST_TEXT;
            default: throw new UnknownFormatOptionException();
        }
    }
}
