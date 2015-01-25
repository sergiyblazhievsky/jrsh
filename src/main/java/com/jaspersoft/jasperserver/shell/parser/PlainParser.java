package com.jaspersoft.jasperserver.shell.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
public class PlainParser {
    public Map<String, List<String>> parse(String... splits) {
        List<String> dictionary = new ArrayList<>(asList("help", "import", "export"));
        Map<String, List<String>> context = new HashMap<>();
        context.put("default", new ArrayList<>());
        boolean anonParam = true;
        String commandName = "";
        for (String v : splits) {
            if (dictionary.contains(v)) {
                commandName = v;
                anonParam = false;
                context.put(commandName, new ArrayList<>());
            } else if (anonParam){
                context.get("default").add(v);
            } else {
                context.get(commandName).add(v);
            }
        }
        return context;
    }

    public static void main(String[] args) {
        Map<String, List<String>> map =
                new PlainParser()
                        .parse("import", "--path", "/a/b/c", "export", "--file", "/folder/file.zip");
        System.out.println(map);
    }
}