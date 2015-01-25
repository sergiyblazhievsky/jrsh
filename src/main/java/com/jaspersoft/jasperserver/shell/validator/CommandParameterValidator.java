package com.jaspersoft.jasperserver.shell.validator;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.LoginCommand;
import com.jaspersoft.jasperserver.shell.exception.parser.MandatoryParameterException;
import com.jaspersoft.jasperserver.shell.exception.parser.ParameterValueSizeException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;

import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class CommandParameterValidator {

    public boolean validate(Command command) {
        if (validateMandatoryParameterValue(command)) {
            validateParameterMultipleValue(command);
        }
        return true;
    }

    // protected due to the testing needs
    protected void validateParameterMultipleValue(Command command) {
        List<Parameter> params = command.getParameters();
        for (Parameter p : params) {
            if (!p.isMultiple() && p.getValues().size() > 1) {
                throw new ParameterValueSizeException(p.getName(), command.getName());
            }
        }
    }

    protected boolean validateMandatoryParameterValue(Command command) {
        if (command instanceof LoginCommand) return true; // a special case // don't need to validate param for login :: ?
        List<Parameter> params = command.getParameters();
        for (Parameter p : params) {
            if (!p.isOptional() && p.getValues().size() == 0) {
                throw new MandatoryParameterException(p.getName(), command.getName());
            }
        }
        return true;
    }
}
