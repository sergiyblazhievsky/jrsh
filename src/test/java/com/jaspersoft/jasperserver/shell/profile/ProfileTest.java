package com.jaspersoft.jasperserver.shell.profile;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test
public class ProfileTest {

    public void should_check_if_profile_is_empty() {
        Profile profile = new Profile();
        assertTrue(profile.isEmpty());
    }
}