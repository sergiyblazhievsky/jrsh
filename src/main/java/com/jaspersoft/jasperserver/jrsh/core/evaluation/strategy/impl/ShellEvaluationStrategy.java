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
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.OperationGrammarParser;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import jline.console.completer.Completer;
import jline.console.history.FileHistory;
import jline.console.history.History;

import java.io.File;
import java.io.IOException;

/**
 * @author Alex Krasnyanskiy
 */
public class ShellEvaluationStrategy extends AbstractEvaluationStrategy {

    private ConsoleReader console;

    public ShellEvaluationStrategy() {
        try {
            FileHistory history = new FileHistory(new File("history/.jrshhistory"));
            this.console = new ConsoleBuilder()
                    .withPrompt("$> ")
                    .withHandler(new JrshCompletionHandler())
                    .withInterruptHandling()
                    .withCompleter(getCompleter())
                    .withHistory(history)
                    .build();
        } catch (IOException e) {
            System.err.println("WARNING: Failed to write operation history file: " + e.getMessage());
        }
    }

    @Override
    public OperationResult eval(Script script) {
        String line = script.getSource().get(0);
        Operation operation = null;

        /*
        Signal interruptSignal = new Signal("INT");
        Signal.handle(interruptSignal, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                logout();
                System.exit(1);
            }
        });
        */


        // Hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                History h = console.getHistory();
                if (h instanceof FileHistory) {
                    try {
                        ((FileHistory) h).flush();
                    } catch (IOException e) {
                        System.err.println("WARNING: Failed to write command history file: " + e.getMessage());
                    }
                }
            }
        }));


        OperationResult result = null;
        while (true) {
            try {
                //
                // Use shared session for all operations
                //
                Session session = SessionFactory.getSharedSession();
                if (line == null) {
                    line = console.readLine();
                }
                if (line.isEmpty()) {
                    print("");
                } else {
                    //
                    // Evaluate operation
                    //
                    operation = parser.parse(line);
                    OperationResult temp = result;
                    result = operation.eval(session);
                    result.setPrevious(temp);
                    print(result.getResultMessage());
                    //
                    // Check initial login
                    //
                    if (operation instanceof LoginOperation && LoginOperation.counter < 1) {
                        return new OperationResult(result.getResultMessage(), ResultCode.FAILED, operation, null);
                    }
                }
                line = null;
            } catch (OperationParseException | IOException err) {
                try {
                    print(err.getMessage());
                } catch (IOException ignored) {
                } finally {
                    line = null;
                }
            } catch (UserInterruptException unimportant) {
                logout();
                return new OperationResult("Interrupted by user.", ResultCode.INTERRUPTED, operation, null);
            } finally {
                operation = null;
            }
        }
    }

    protected void print(String message) throws IOException {
        console.println(message);
        console.flush();
    }

    protected Completer getCompleter() {
        CompleterBuilder completerConverter = new CompleterBuilder();
        //
        // Collect grammar from the operations
        //
        for (Operation operation : OperationFactory.createOperationsByAvailableTypes()) {
            Grammar grammar = OperationGrammarParser.parse(operation);
            //
            // And use it in builder to get general aggregated
            // completer
            //
            completerConverter.withOperationGrammar(grammar);
        }

        return completerConverter.build();
    }

    protected void logout() {
        SessionFactory.getSharedSession().logout();
    }
}
