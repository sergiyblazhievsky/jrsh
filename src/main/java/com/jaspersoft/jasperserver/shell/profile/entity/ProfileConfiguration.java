package com.jaspersoft.jasperserver.shell.profile.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Krasnyanskiy
 */
@Data
public class ProfileConfiguration {
    private String defaultProfile;
    private Set<Profile> profiles;

    public ProfileConfiguration() {
        profiles = new HashSet<>();
    }
}
