package com.jaspersoft.jasperserver.shell.exception.parser;

/**
 * @author Alexander Krasnyanskiy
 */
public class MandatoryParameterException extends ParserException {

    public MandatoryParameterException(String paramName, String cmdName) {
        //super(format("You should specify a value for %s parameter of command '%s'", paramName, cmdName));
        super(cmdName);
    }
}
