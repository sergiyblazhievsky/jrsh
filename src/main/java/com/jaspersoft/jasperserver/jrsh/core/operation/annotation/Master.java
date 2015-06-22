package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Master {

    String name() default "";

    boolean tail() default false;

    String description() default "Not specified.";

    String usage() default "Not specified.";

    Class<? extends Token> tokenClass() default StringToken.class;

}

