package com.jaspersoft.jasperserver.shell.parameter;

import org.testng.Assert;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;

@Test
public class ParameterTest {

    public void should_return_proper_values() {

        Parameter param = new Parameter();

        param.setAvailable(true);
        param.setKey("key");
        param.setMultiple(true);
        param.setName("name");
        param.setOptional(true);
        param.setValues(asList("v1", "v2"));

        Assert.assertEquals(param.getKey(), "key");
        Assert.assertEquals(param.getName(), "name");
        Assert.assertTrue(param.isMultiple());
        Assert.assertTrue(param.isAvailable());
        Assert.assertTrue(param.isOptional());
        Assert.assertTrue(param.getValues().size() == 2);
    }
}