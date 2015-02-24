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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileConfiguration that = (ProfileConfiguration) o;

        if (defaultProfile != null ? !defaultProfile.equals(that.defaultProfile) : that.defaultProfile != null)
            return false;
        if (profiles != null ? !profiles.equals(that.profiles) : that.profiles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = defaultProfile != null ? defaultProfile.hashCode() : 0;
        result = 31 * result + (profiles != null ? profiles.hashCode() : 0);
        return result;
    }
}
