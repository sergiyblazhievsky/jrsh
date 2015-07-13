package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenPreconditions {
    public static boolean isConnectionString(String token) {
        Pattern pattern = Pattern.compile("(\\w+[|])?\\w+[%]\\w+[@]\\S+");
        Matcher matcher = pattern.matcher(token);
        return matcher.matches();
    }

    public static boolean isScriptFileName(String token) {
        Pattern scriptPattern = Pattern.compile("\\S+(.jrs)$");
        Matcher scriptMatcher = scriptPattern.matcher(token);
        return scriptMatcher.matches();
    }
}
