package com.jaspersoft.jasperserver.shell.validator;

import com.jaspersoft.jasperserver.shell.exception.parser.WrongRepositoryPathFormatException;
import org.testng.annotations.Test;

public class RepositoryPathValidatorUtilTest {

    @Test(expectedExceptions = {WrongRepositoryPathFormatException.class})
    public void should_validate_path() {
        RepositoryPathValidatorUtil.validate("path");
    }

    @Test(expectedExceptions = {WrongRepositoryPathFormatException.class})
    public void should_throw_exception_if_path_ends_with_slash() {
        RepositoryPathValidatorUtil.validate("path/");
    }
}