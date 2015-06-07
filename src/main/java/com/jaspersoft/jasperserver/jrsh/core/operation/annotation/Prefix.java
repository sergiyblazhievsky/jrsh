package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alexander Krasnyanskiy
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Prefix {

    String value() default "";

    Class<? extends Token> tokenClass() default StringToken.class;

}