package com.jaspersoft.jasperserver.shell.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Alexander Krasnyanskiy
 */
@Getter
@Setter
@Accessors(chain = true)
public class Profile {
    private String name;
    private String url;
    private String username;
    private String organization;
}