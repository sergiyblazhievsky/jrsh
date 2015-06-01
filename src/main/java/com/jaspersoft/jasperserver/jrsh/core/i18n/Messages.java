package com.jaspersoft.jasperserver.jrsh.core.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alex Krasnyanskiy
 */
public class Messages {

    public static Locale locale = Locale.ROOT;
    private String bundleName;

    public Messages(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getMessage(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        return bundle.getString(key);
    }
}
