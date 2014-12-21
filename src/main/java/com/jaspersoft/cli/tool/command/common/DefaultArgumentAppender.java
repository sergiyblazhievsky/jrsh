package com.jaspersoft.cli.tool.command.common;

/**
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
public class DefaultArgumentAppender {

    /**
     * Add jrs.sh command name to args array
     * @param args passed args
     * @return new array with `jrs.sh` command name
     */
    public static String[] append(String... args) {
        String[] converted = new String[args.length + 1];
        converted[0] = "jrs";
        for (int i = 0, j = 1; i < args.length; i++, j++) {
            converted[j] = args[i];
        }
        return converted;
    }
}
