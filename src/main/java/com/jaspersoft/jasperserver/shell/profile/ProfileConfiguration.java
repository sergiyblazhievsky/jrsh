package com.jaspersoft.jasperserver.shell.profile;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileConfiguration {
    private List<Profile> profiles = new ArrayList<>();
}