package com.jaspersoft.cli.tool.command.common;

import com.beust.jcommander.IStringConverter;
import com.jaspersoft.cli.tool.command.impl.ShowCommand.OutputFormat;
import com.jaspersoft.cli.tool.exception.UnknownFormatOptionException;

/**
* @author Alex Krasnyanskiy
*/
public class OptionConverter implements IStringConverter<OutputFormat> {
    @Override
    public OutputFormat convert(String value) {
        switch (value) {
            case "json": return OutputFormat.JSON;
            case "text": return OutputFormat.TEXT;
            case "list": return OutputFormat.LIST_TEXT;
            default: throw new UnknownFormatOptionException();
        }
    }
}
