package com.jaspersoft.jasperserver.jrsh.core;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.impl.ShellEvaluationStrategyTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperationTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.ConditionsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConditionsTest.class,
        LoginOperationTest.class,
        ShellEvaluationStrategyTest.class
})
public class UnitTestSuite {
}
