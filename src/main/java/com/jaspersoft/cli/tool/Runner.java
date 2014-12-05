package com.jaspersoft.cli.tool;

import com.jaspersoft.cli.tool.core.CLIEngine;

/**
 * @author Alexander Krasnyanskiy
 */
public class Runner {
    public static void main(String[] args) {
        new CLIEngine(args).run();
    }
}