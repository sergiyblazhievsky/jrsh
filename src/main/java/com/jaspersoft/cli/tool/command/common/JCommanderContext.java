package com.jaspersoft.cli.tool.command.common;

import com.beust.jcommander.JCommander;

/**
 * @author Alex Krasnyanskiy
 */
public class JCommanderContext {
    private static JCommander instance = new JCommander();

    private JCommanderContext() {
    }

    public static JCommander getInstance() {
        return instance;
    }
}
