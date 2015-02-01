package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.exception.CannotSaveProfileConfiguration;
import com.jaspersoft.jasperserver.shell.exception.profile.CannotLoadProfileConfiguration;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.Profile;
import com.jaspersoft.jasperserver.shell.profile.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.ProfileConfigurationFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import static com.jaspersoft.jasperserver.shell.profile.ProfileConfigurationFactory.get;
import static com.jaspersoft.jasperserver.shell.profile.ProfileUtil.persist;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileCommand extends Command {

    private static final String FILE = "/Users/alexkrasnyaskiy/IdeaProjects/jasperserver-shell/src/main/resources/jrsh-profile.yml";

    public ProfileCommand() {
        name = "profile";
        description = "Show current profile information.";
        parameters.add(new Parameter().setName("anonymous").setMultiple(true).setOptional(true));
        parameters.add(new Parameter().setName("load").setOptional(true));
        parameters.add(new Parameter().setName("save").setOptional(true));
        parameters.add(new Parameter().setName("list").setOptional(true));
    }

    @Override
    void run() {
        // mandatory profile properties
        if (profile.getUrl() == null && profile.getUsername() == null) {
            if (!parameter("anonymous").isAvailable()
                    && !parameter("load").isAvailable()
                    && !parameter("save").isAvailable()
                    && !parameter("list").isAvailable()) {
                out.println("Not available.");
            }
        }
        if (parameter("load").isAvailable()) {
            try {
                ProfileConfigurationFactory.create(FILE);
                out.println("Loaded.");
            } catch (FileNotFoundException e) {
                throw new CannotLoadProfileConfiguration();
            }
        } else if (parameter("save").isAvailable()) {
            ProfileConfiguration cfg = get();
            if ("default".equals(profile.getName())) {
                String n = null;
                if (parameter("anonymous").isAvailable()) {
                    n = parameter("anonymous").getValues().get(0);
                }
                cfg.getProfiles().add(profile.setName(n == null ? "Profile-" + new Random().nextInt(Integer.MAX_VALUE) : n));
                try {
                    persist(cfg, FILE);
                } catch (IOException e) {
                    throw new CannotSaveProfileConfiguration();
                }
                out.println("Saved.");
            } else if (profile.getName() != null){
                out.println("It is already saved.");
            } else {
                out.println("You need to load profile configuration first.");
            }
        } else if (parameter("list").isAvailable()) {
            ProfileConfiguration cfg = get();
            if (cfg == null) {
                out.println("You need to load profile configuration first.");
            } else {
                for (Profile p : cfg.getProfiles()) {
                    out.printf("\t%s\n", p.getName());
                }
            }
        } else if (profile.getUrl() != null && profile.getUsername() != null){
            out.printf("\nprofile name:\t%s" + "\nserver url:\t%s" + "\nusername:\t%s" + "\norganization:\t%s\n\n",
                    profile.getName(),
                    profile.getUrl(),
                    profile.getUsername(),
                    profile.getOrganization() == null ? "unknown" : profile.getOrganization()
            );
        }
    }
}
