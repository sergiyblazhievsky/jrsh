package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory;
import com.jaspersoft.jasperserver.shell.profile.util.ProfileUtil;

import java.io.IOException;
import java.util.List;

import static com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory.getConfiguration;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileCommand extends Command {

    // TODO: implement me!

    public ProfileCommand() {
        name = "profile";
        description = "Show current profile information.";
        parameters.add(new Parameter().setName("anonymous").setMultiple(true).setOptional(true));
        parameters.add(new Parameter().setName("load").setOptional(true));
        parameters.add(new Parameter().setName("save").setOptional(true));
        parameters.add(new Parameter().setName("default").setOptional(true));
        parameters.add(new Parameter().setName("list").setOptional(true));
    }

    @Override
    public void run() {
        if (profile.getUrl() == null && profile.getUsername() == null) {
            if (!parameter("anonymous").isAvailable()
                    && !parameter("load").isAvailable()
                    && !parameter("save").isAvailable()
                    && !parameter("list").isAvailable()) {
                out.println("Not available.");
            }
        }

        if (parameter("load").isAvailable()) {


            // todo!


        } else if (parameter("save").isAvailable()) {
            ProfileConfiguration cfg = ProfileConfigurationFactory.getConfiguration();
            if ("current".equals(profile.getName())) {


                // todo!


            } else if (profile.getName() != null) {
                out.println("It is already saved.");
            } else {
                out.println("You need to load profile configuration first.");
            }
        } else if (parameter("list").isAvailable()) {
            ProfileConfiguration cfg = getConfiguration();
            if (cfg == null) out.println("You need to load profile configuration first.");
            else for (Profile p : cfg.getProfiles())
                out.printf(cfg.getDefaultProfile().equals(p.getName())
                        ? "\t%s \u001B[31m*\u001B[0m\n"
                        : "\t%s\n", p.getName());
        } else if (parameter("default").isAvailable()) {
            ProfileConfiguration cfg = getConfiguration();
            List<String> vals = parameter("anonymous").getValues();
            if (vals.isEmpty()) {
                throw new RuntimeException("Oops!");
            } else {
                String value = vals.get(0);
                String founded = "";
                for (Profile p : cfg.getProfiles()) {
                    if (p.getName().equals(value)) {
                        founded = value;
                    }
                }
                if (founded.isEmpty()) {
                    throw new RuntimeException("There is no profile with such name!");
                }
                cfg.setDefaultProfile(founded);
                try {
                    ProfileUtil.persist(cfg, "/usr/conf/profiles.yml");
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } else if (profile.getUrl() != null && profile.getUsername() != null) {
            out.printf("\nprofile name:\t%s" + "\nserver url:\t%s" + "\nusername:\t%s" + "\norganization:\t%s\n\n",
                    profile.getName(),
                    profile.getUrl(),
                    profile.getUsername(),
                    profile.getOrganization() == null ? "unknown" : profile.getOrganization()
            );
        }
    }
}
