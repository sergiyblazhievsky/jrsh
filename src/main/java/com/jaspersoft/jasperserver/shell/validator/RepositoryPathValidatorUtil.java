package com.jaspersoft.jasperserver.shell.validator;

import com.jaspersoft.jasperserver.shell.exception.parser.WrongRepositoryPathFormatException;

/**
 * @author Alexander Krasnyanskiy
 */
public class RepositoryPathValidatorUtil {
    public static void validate(String path) {

        if ("".equals(path)) return; // print form the root case

        char last = path.charAt(path.length() - 1);
        char first = path.charAt(0);

        if (last == '/' || last == '\\') {
            throw new WrongRepositoryPathFormatException();
        }

        if (first != '/' && first != '\\') {
            throw new WrongRepositoryPathFormatException();
        }
    }
}
