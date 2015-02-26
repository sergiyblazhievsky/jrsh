package com.jaspersoft.jasperserver.shell.command.impl;

import com.jaspersoft.jasperserver.shell.command.Command;
import com.jaspersoft.jasperserver.shell.completion.CompletionConfigurer;
import com.jaspersoft.jasperserver.shell.context.Context;
import com.jaspersoft.jasperserver.shell.exception.CannotSaveProfileConfigurationException;
import com.jaspersoft.jasperserver.shell.exception.NoProfileWithSuchNameException;
import com.jaspersoft.jasperserver.shell.exception.NotSpecifiedProfileNameException;
import com.jaspersoft.jasperserver.shell.exception.profile.CannotSaveProfileException;
import com.jaspersoft.jasperserver.shell.exception.profile.NotUniqueProfileNameException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.reader.ProfileReader;
import com.jaspersoft.jasperserver.shell.profile.writer.ProfileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory.getConfiguration;
import static java.lang.System.out;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileCommand extends Command {

    private Properties properties;
    private ProfileWriter writer;
    private ProfileReader reader;

    public ProfileCommand() {
        name = "profile";
        description = "Allows manipulate of user profiles.";
        parameters.add(new Parameter().setName("anonymous").setMultiple(true).setOptional(true));
        parameters.add(new Parameter().setName("load").setOptional(true));
        parameters.add(new Parameter().setName("save").setOptional(true));
        parameters.add(new Parameter().setName("default").setOptional(true));
        parameters.add(new Parameter().setName("list").setOptional(true));

        initProperties();
        writer = new ProfileWriter(System.getenv("JRSH_HOME") + properties.getProperty("jrsh.config.path"));
        reader = new ProfileReader(System.getenv("JRSH_HOME") + properties.getProperty("jrsh.config.path"));
    }

    private void initProperties() {
        properties = new Properties();
        InputStream stream = Context.class.getClass().getResourceAsStream("/config.properties");
        try {
            properties.load(stream);
        } catch (IOException ignored) {/* NOP */}
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
            List<String> names = parameter("anonymous").getValues();
            String profileName = names.isEmpty() ? "" : names.get(0);
            ProfileConfiguration cfg = reader.read();

            if (profileName == null || profileName.isEmpty()) {
                throw new NotSpecifiedProfileNameException();
            }

            // if current in-mem config is different than loaded then merge them
            if (!cfg.equals(getConfiguration())) {
                getConfiguration().setProfiles(cfg.getProfiles());
                getConfiguration().setDefaultProfile(cfg.getDefaultProfile());
            }

            Profile loaded = null;
            for (Profile p : cfg.getProfiles()) {
                if (p.getName().equals(profileName)) {
                    loaded = p;
                }
            }
            if (loaded == null) {
                throw new NoProfileWithSuchNameException(profileName);
            } else {
                // copy profile
                profile.setName(loaded.getName());
                profile.setOrganization(loaded.getOrganization());
                profile.setUrl(loaded.getUrl());
                profile.setUsername(loaded.getUsername());

                System.out.println("Loaded.");
            }
        } else if (parameter("save").isAvailable()) {
            List<String> names = parameter("anonymous").getValues();
            if (names.isEmpty()) {
                throw new NotSpecifiedProfileNameException();
            }
            String profileName = names.get(0); // defined by user
            ProfileConfiguration cfg = reader.read();
            for (Profile profile : cfg.getProfiles()) {
                if (profile.getName().equals(profileName)) {
                    throw new NotUniqueProfileNameException();
                }
            }
            String tmpName = profile.getName();
            profile.setName(profileName);
            cfg.getProfiles().add(profile);
            try {
                writer.write(cfg);
                CompletionConfigurer.available.getStrings().add(profileName);
                System.out.println("Saved.");
            } catch (IOException e) {
                profile.setName(tmpName);
                throw new CannotSaveProfileException();
            }
        } else if (parameter("list").isAvailable()) {
            ProfileConfiguration cfg = reader.read();
            if (cfg == null) {
                out.println("You need to load profile configuration first.");
            } else {

                List<String> names = parameter("anonymous").getValues();
                if (names.size() > 0) {
                    boolean foundProfile = false;
                    for (String profileName : names) {
                        for (Profile profile : cfg.getProfiles()) {
                            if (profile.getName().equals(profileName)) {
                                foundProfile = true;
                                out.printf("\nProfile name:\t%s" + "\nServer url:\t%s" + "\nUsername:\t%s" + "\nOrganization:\t%s\n\n",
                                        profile.getName(),
                                        profile.getUrl(),
                                        profile.getUsername(),
                                        profile.getOrganization() == null ? "\u001B[31mundefined\u001B[0m" : profile.getOrganization()
                                );
                            }
                        }
                    }
                    if (!foundProfile) { // there is nothing to show
                        out.printf("No profile found for given input.");
                    }
                }
                if (names.isEmpty()) {
                    // print profile list
                    for (Profile p : cfg.getProfiles()) {
                        out.printf(cfg.getDefaultProfile().equals(p.getName()) ? "\t%s \u001B[31m*\u001B[0m\n" : "\t%s\n", p.getName());
                    }
                }
            }
        } else if (parameter("default").isAvailable()) {
            ProfileConfiguration cfg = reader.read();
            List<String> names = parameter("anonymous").getValues();
            if (names.isEmpty()) {
                throw new NotSpecifiedProfileNameException();
            } else {
                String value = "";
                if (names.size() > 1) {
                    value = names.get(1);
                } else if (names.size() == 1) {
                    value = names.get(0);
                }
                String founded = "";
                for (Profile p : cfg.getProfiles()) {
                    if (p.getName().equals(value)) {
                        founded = value;
                    }
                }
                if (founded.isEmpty()) {
                    throw new NoProfileWithSuchNameException(value);
                }
                cfg.setDefaultProfile(founded);
                try {
                    writer.write(cfg);
                    System.out.println("Saved.");
                } catch (IOException e) {
                    throw new CannotSaveProfileConfigurationException();
                }
            }
        } else if (profile.getUrl() != null && profile.getUsername() != null) {
            out.printf("\nProfile name:\t%s" + "\nServer url:\t%s" + "\nUsername:\t%s" + "\nOrganization:\t%s\n\n",
                    profile.getName(),
                    profile.getUrl(),
                    profile.getUsername(),
                    profile.getOrganization() == null ? "\u001B[31mundefined\u001B[0m" : profile.getOrganization()
            );
        }
    }
}
