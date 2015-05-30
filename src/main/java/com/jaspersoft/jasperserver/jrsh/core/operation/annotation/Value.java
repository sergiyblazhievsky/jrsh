package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.InputToken;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alex Krasnyanskiy
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Value {

    String tokenAlias()
    default "";

    String tokenValue()
    default "";

    boolean tail()
    default false;

    Class<? extends Token> tokenClass()
    default InputToken.class;

}
