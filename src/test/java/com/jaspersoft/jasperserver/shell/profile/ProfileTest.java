package com.jaspersoft.jasperserver.shell.profile;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileTest {

    @Test
    public void should_check_if_profile_is_empty() {
        Profile profile = new Profile();
        Assert.assertTrue(profile.isEmpty());
    }
}