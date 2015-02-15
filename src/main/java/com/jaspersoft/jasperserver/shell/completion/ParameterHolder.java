package com.jaspersoft.jasperserver.shell.completion;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Krasnyanskiy
 */
public class ParameterHolder {

    private static final Set<String> PARAMS = new HashSet<>();

    private ParameterHolder() {/* NOP */}

    public static Collection<String> getParams() {
        return PARAMS;
    }
}
