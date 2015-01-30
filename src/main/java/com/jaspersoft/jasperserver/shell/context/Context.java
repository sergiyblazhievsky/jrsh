package com.jaspersoft.jasperserver.shell.context;

import com.jaspersoft.jasperserver.shell.command.Command;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.shell.factory.CommandFactory.create;
import static java.util.Arrays.asList;

@Data
public class Context {

    //private Properties properties;
    private Map<String, String> cmdDescription = new HashMap<>();
    private List<String> dictionary
            = new ArrayList<>(asList("help", "?", "import", "export",
            "profile", "session", "logout", "login",
            "exit", "show"));

    public Context() {
        //properties = System.getProperties();
        //properties.load(new FileInputStream("MessagesBundle_en_US.properties"));
        for (String v : dictionary) {
            Command c = create(v);
            cmdDescription.put(c.getName(), c.getDescription());
        }
    }
}
