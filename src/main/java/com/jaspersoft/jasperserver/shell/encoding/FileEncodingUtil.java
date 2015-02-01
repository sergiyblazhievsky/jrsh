package com.jaspersoft.jasperserver.shell.encoding;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * @author Alexander Krasnyanskiy
 */
public class FileEncodingUtil {

    public static void setUTF8() {
        try {
            System.setProperty("file.encoding", "UTF-8");
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
