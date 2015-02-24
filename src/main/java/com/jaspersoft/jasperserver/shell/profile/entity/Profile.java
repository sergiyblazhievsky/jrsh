package com.jaspersoft.jasperserver.shell.profile.entity;

/**
 * @author Alexander Krasnyanskiy
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (name != null ? !name.equals(profile.name) : profile.name != null) return false;
        if (organization != null ? !organization.equals(profile.organization) : profile.organization != null)
            return false;
        if (url != null ? !url.equals(profile.url) : profile.url != null) return false;
        if (username != null ? !username.equals(profile.username) : profile.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        return result;
    }
}