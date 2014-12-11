package com.jaspersoft.cli.tool.core.v2.common;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

/**
 * @author Alexander Krasnyanskiy
 */
public class OptionParser {
    private CommandLineParser parser;

    public OptionParser() {
        parser = new BasicParser();
    }

    public Option[] parse(String[] args) throws ParseException {
        return parser.parse(null, args).getOptions();
    }
}
