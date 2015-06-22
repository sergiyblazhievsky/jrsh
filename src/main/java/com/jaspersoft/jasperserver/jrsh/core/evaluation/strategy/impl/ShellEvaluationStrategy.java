package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.common.ConsoleBuilder;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import com.jaspersoft.jasperserver.jrsh.core.completion.CompleterBuilder;
import com.jaspersoft.jasperserver.jrsh.core.completion.JrshCompletionHandler;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.OperationGrammarParser;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import jline.console.completer.Completer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.FAILED;
import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.INTERRUPTED;

/**
 * This class represents an algorithm of the operation, which represents
 * an interactive mode of application. In interactive mode user can
 * execute only one command per line, or exit by pressing Ctrl+C.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShellEvaluationStrategy extends AbstractEvaluationStrategy {

    private ConsoleReader console;

    public ShellEvaluationStrategy() {
        this.console = new ConsoleBuilder()
                .withPrompt("$> ")
                .withHandler(new JrshCompletionHandler())
                .withInterruptHandling()
                .withCompleter(getCompleter())
                .build();
    }

    @Override
    public OperationResult eval(Script script) {
        String line = script.getSource().get(0);
        Operation operation = null;
        OperationResult result = null;
        while (true) {
            try {
                Session session = SessionFactory.getSharedSession();
                if (line == null) {
                    line = console.readLine();
                }
                if (line.isEmpty()) {
                    print("");
                } else {
                    OperationResult temp = result;
                    operation = parser.parse(line);
                    result = operation.execute(session);

                    // Let's create a chained result
                    result.setPrevious(temp);

                    // In fact, this is optional action. We don't
                    // have to do that here. Instead of printing result
                    // we could pass it up to the invoker
                    print(result.getResultMessage());

                    if (result.getResultCode() == FAILED) {

                        // Check initial login
                        if (operation instanceof LoginOperation){
                            return new OperationResult(result.getResultMessage(), FAILED, operation, null);
                        } else {
                            Master master = operation.getClass().getAnnotation(Master.class);
                            String usage = master.usage();
                            print("usage: " + usage);
                        }
                    }
                }
                line = null;
            } catch (UserInterruptException unimportant) {
                logout();
                return new OperationResult("Interrupted by user", INTERRUPTED, operation, null);
            } catch (OperationParseException err) {
                try {
                    print(err.getMessage());
                } finally {
                    line = null;
                }
            } catch (IOException ignored) {
            } finally {
                operation = null;
            }
        }
    }

    //---------------------------------------------------------------------
    // Helper methods
    //---------------------------------------------------------------------

    protected void print(String message) {
        try {
            console.println(message);
            console.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Completer getCompleter() {
        CompleterBuilder completerBuilder = new CompleterBuilder();
        for (Operation operation : OperationFactory.createOperationsByAvailableTypes()) {
            Grammar grammar = OperationGrammarParser.parse(operation);
            completerBuilder.withOperationGrammar(grammar);
        }
        return completerBuilder.build();
    }

    protected void logout() {
        SessionFactory.getSharedSession().logout();
    }
}
