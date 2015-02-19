package com.jaspersoft.jasperserver.shell.completion;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class CollectionUtil {

    public static List<String> convert(List<Pair<String, Boolean>> col) {
        List<String> list = new ArrayList<>();
        for (Pair<String, Boolean> pair : col) {
            list.add(pair.getKey());
        }
        return list;
    }
}
