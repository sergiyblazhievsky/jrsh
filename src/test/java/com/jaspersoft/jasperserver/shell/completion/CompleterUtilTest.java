package com.jaspersoft.jasperserver.shell.completion;

import com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil}
 */
public class CompleterUtilTest {

    @DataProvider
    public Object[][] stringDiff() {
        return new Object[][]{{"abcdef", "def"}, {"abcxxx", "xxx"}, {"abcyyy", "yyy"}};
    }

    @Test(dataProvider = "stringDiff")
    /**
     * for {@link com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil#diff(String, String)}
     */
    public void should_return_proper_difference_string(String given, String expected) {
        String diff = CompleterUtil.diff("abc", given);
        Assert.assertEquals(diff, expected);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil#commonSubstring(String, java.util.Collection)}
     */
    public void should_return_common_substring() {
        String subString = CompleterUtil.commonSubstring("lo", asList("login", "logout"));
        Assert.assertEquals(subString, "log");

        subString = CompleterUtil.commonSubstring("price", asList("price$100", "price$150"));
        Assert.assertEquals(subString, "price$1");

        subString = CompleterUtil.commonSubstring("xx", asList("xxyy", "xxzz"));
        Assert.assertEquals(subString, "xx");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil#match(String, java.util.Collection)}
     */
    public void should_match_string() {
        Assert.assertTrue(CompleterUtil.match("xx", asList("xx", "zz")));
        Assert.assertFalse(CompleterUtil.match("xx", asList("__", "zz")));
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.shell.completion.util.CompleterUtil#filter(String, java.util.Collection)}
     */
    public void should_filter_collection() {
        Collection<String> filtered = CompleterUtil.filter("aabb", asList("aabbcc", "aabbff", "aabbzz", "abc", "xyz"));
        Assert.assertSame(filtered.size(), 3);
    }
}
