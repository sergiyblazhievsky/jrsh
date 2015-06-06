package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alexander Krasnyanskiy
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    String name() default "";

    boolean mandatory() default false;

    String[] dependsOn() default {};

    Value[] values() default {};

    String[] ruleGroups() default {"COMMON_GROUP"};

}
