package com.jaspersoft.jasperserver.shell.validator;

import com.jaspersoft.jasperserver.shell.command.ExportCommand;
import com.jaspersoft.jasperserver.shell.exception.parser.MandatoryParameterException;
import com.jaspersoft.jasperserver.shell.parameter.Parameter;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ParameterValidatorTest {

    private ParameterValidator validator;

    @BeforeMethod
    public void before() {
        validator = new ParameterValidator();
    }

    public void should_validate_command() {

        /* Given */
        ExportCommand export = new ExportCommand();
        Parameter anon = export.getParameters().get(0);
        anon.getValues().add("/public/Samples/Dashboards/2._Performance_Summary_Dashboard");
        anon.setAvailable(true);

        /* Then */
        Assert.assertTrue(/* When */validator.validate(export));
    }

    @Test(enabled = false, expectedExceptions = {MandatoryParameterException.class})
    public void should_throw_exception_if_mandatory_parameter_is_missing() {

        /* Given */
        ExportCommand export = new ExportCommand();

        /* Then */
        Assert.assertTrue(/* When */validator.validate(export));
    }

    @AfterMethod
    public void after() {
        validator = null;
    }
}