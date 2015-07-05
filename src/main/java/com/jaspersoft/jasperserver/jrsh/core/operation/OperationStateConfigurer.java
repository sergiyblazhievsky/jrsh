package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.CannotFindSetterException;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import lombok.val;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Configures the state of operation based on
 * provided tokens.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public class OperationStateConfigurer {

    public static void configure(Operation operation, List<Token> ruleTokens, List<String> inputTokens) {
        val clazz = operation.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Parameter param = field.getAnnotation(Parameter.class);
            if (param != null) {
                Value[] values = param.values();
                for (Value value : values) {
                    String alias = value.tokenAlias();
                    int idx = getTokenIndex(ruleTokens, alias);
                    if (idx >= 0) {
                        field.setAccessible(true);
                        Method setter = findSetter(clazz.getMethods(), field.getName());
                        if (setter == null) {
                            throw new CannotFindSetterException(field.getName());
                        }
                        try {
                            setter.invoke(operation, inputTokens.get(idx));
                        } catch (Exception err) {
                            //
                            // Reflection wraps any custom exceptions,
                            // however we can get them through the cause
                            //
                            checkErrorType(err);
                        }
                        field.setAccessible(false);
                    }
                }
            }
        }
    }

    protected static void checkErrorType(Exception err) {
        Throwable cause = err.getCause();
        if (OperationParseException.class.isAssignableFrom(cause.getClass())) {
            throw (RuntimeException) cause;
        }
    }

    protected static int getTokenIndex(List<Token> tokens, String tokenName) {
        for (int idx = 0; idx < tokens.size(); idx++) {
            Token token = tokens.get(idx);
            if (tokenName.equals(token.getName())) {
                return idx;
            }
        }
        return -1;
    }

    protected static Method findSetter(Method[] methods, String name) {
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && methodName.toLowerCase().endsWith(name.toLowerCase())) {
                return method;
            }
        }
        return null;
    }
}
