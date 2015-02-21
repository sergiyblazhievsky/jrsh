package com.jaspersoft.jasperserver.shell.profile.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Alexander Krasnyanskiy
 */
@Data
@Accessors(chain = true)
public class Profile {
    private String name;
    private String url;
    private String username;
    private String organization;

    public Profile() {
    }

    public Profile(String name, String url, String username) {
        this.name = name;
        this.url = url;
        this.username = username;
    }

    public Profile(String name, String url, String username, String organization) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.organization = organization;
    }
}