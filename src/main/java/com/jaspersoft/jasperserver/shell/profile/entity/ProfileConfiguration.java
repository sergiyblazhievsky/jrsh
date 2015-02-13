package com.jaspersoft.jasperserver.shell.profile.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileConfiguration {

    private String defaultProfile;
    private Set<Profile> profiles;

    public ProfileConfiguration() {
        profiles = new HashSet<>();
    }

    public String getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(String defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }
}
