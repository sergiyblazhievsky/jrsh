package com.jaspersoft.jasperserver.shell.parser;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.command.impl.HelpCommand;
import com.jaspersoft.jasperserver.shell.command.impl.LoginCommand;
import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.context.ContextAware;
import com.jaspersoft.jasperserver.shell.exception.parser.UnknownInputContentException;
import com.jaspersoft.jasperserver.shell.exception.parser.UnknownParserException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.validator.ParameterValidator;

import java.util.LinkedList;
import java.util.Queue;

import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.createCommand;

/**
 * @author Alexander Krasnyanskiy
 */
public class CommandParser implements ContextAware {

    private Context context;
    private ParameterValidator validator;

    public CommandParser(ParameterValidator validator) {
        this.validator = validator;
    }

    public Queue<Command> parse(String... splits) {
        if (splits.length == 1) {
            splits = splits[0].split("\\s+(?=(?:\"[^\"]*\"|[^\"])*$)");
        }
        Queue<Command> cmdQueue = new LinkedList<>();
        boolean anonymousParam = true;
        Command current = null;
        LoginCommand defaultCmd = null;
        Parameter currentParam = null;
        for (String v : splits) {
            if (context.getDictionary().contains(v)) {
                if (current != null && !(current instanceof HelpCommand)) {
                    cmdQueue.offer(current);
                } else if (current != null) {
                    current.getParameters().get(0).setAvailable(true).getValues().add(v);
                    continue;
                }
                current = createCommand(v);
                if (current instanceof HelpCommand) {
                    ((HelpCommand) current).setContext(context);
                }
                anonymousParam = false;
            } else if (anonymousParam) {
                if (defaultCmd == null) {
                    defaultCmd = new LoginCommand();
                    cmdQueue.offer(defaultCmd);
                }
                Parameter p = defaultCmd.parameter(v);
                if (p != null) {
                    currentParam = p;
                    currentParam.setAvailable(true);
                } else if (currentParam == null) {
                    throw new UnknownInputContentException(v);
                } else {
                    currentParam.getValues().add(v);
                    currentParam.setAvailable(true);
                }
            } else {
                Parameter p = current.parameter(v);
                if (p != null) {
                    if ("anonymous".equals(p.getName())) {
                        p.getValues().add(v);
                        p.setAvailable(true);
                    }
                    currentParam = p;
                    currentParam.setAvailable(true);
                } else if (currentParam == null) {
                    throw new UnknownParserException();
                } else {
                    currentParam.getValues().add(v);
                    currentParam.setAvailable(true);
                }
            }
        }
        cmdQueue.offer(current);
        for (Command command : cmdQueue) {
            if (command != null) {
                validator.validate(command);
            }
        }

        return cmdQueue;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
