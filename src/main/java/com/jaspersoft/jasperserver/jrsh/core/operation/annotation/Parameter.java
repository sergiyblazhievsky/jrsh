package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alex Krasnyanskiy
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    String name()
    default ""; // by default we use a field name

    boolean mandatory()
    default false;

    String[] dependsOn()
    default {};

    Value[] values()
    default {};

    String[] ruleGroups()
    default {"GENERAL_GROUP"};

}
