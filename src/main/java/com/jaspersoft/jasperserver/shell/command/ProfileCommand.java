package com.jaspersoft.jasperserver.shell.command;

import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory;

import java.util.Random;

import static com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory.getConfiguration;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileCommand extends Command {

    // TODO: доделать!

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
        if (profile.getUrl() == null && profile.getUsername() == null) {
            if (!parameter("anonymous").isAvailable()
                    && !parameter("load").isAvailable()
                    && !parameter("save").isAvailable()
                    && !parameter("list").isAvailable()) {
                out.println("Not available.");
            }
        }
        if (parameter("load").isAvailable()) {
            //try {
                //if (parameter("anonymous").isAvailable()) {
                //    file = parameter("anonymous").getValues().get(0);
                    //ProfileConfigurationFactory.createConfiguration(file);
                //} else {
                    //ProfileConfigurationFactory.createConfiguration(file); // fixme :: handle default file path
                //}
                out.println("Loaded.");
            //} catch (FileNotFoundException e) {
            //    throw new CannotLoadProfileConfiguration();
            //}
        } else if (parameter("save").isAvailable()) {
            ProfileConfiguration cfg = ProfileConfigurationFactory.getConfiguration();
            if ("current".equals(profile.getName())) {
                String n = null;
                if (parameter("anonymous").isAvailable()) {
                    n = parameter("anonymous").getValues().get(0);
                }
                cfg.getProfiles().add(profile.setName(n == null ? "Profile-" + new Random().nextInt(Integer.MAX_VALUE) : n));
                //try {
                    //ProfileUtil.persist(cfg, file);
                //} catch (IOException e) {
                //    throw new CannotSaveProfileConfiguration();
                //}
                out.println("Saved.");
            } else if (profile.getName() != null) {
                out.println("It is already saved.");
            } else {
                out.println("You need to load profile configuration first.");
            }
        } else if (parameter("list").isAvailable()) {
            ProfileConfiguration cfg = getConfiguration();
            if (cfg == null) {
                out.println("You need to load profile configuration first.");
            } else {
                for (Profile p : cfg.getProfiles()) {
                    if (cfg.getDefaultProfile().equals(p.getName())){
                        out.printf("\t%s \u001B[31m*\u001B[0m\n", p.getName()); // mark default profile with `*`
                    } else {
                        out.printf("\t%s\n", p.getName());
                    }
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
