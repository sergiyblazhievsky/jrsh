package com.jaspersoft.cli.tool;

import com.jaspersoft.cli.tool.core.CliEngine;

/**
 * Application runner.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class Runner {
    public static void main(String[] args) {
        new CliEngine(args).process();
    }
}