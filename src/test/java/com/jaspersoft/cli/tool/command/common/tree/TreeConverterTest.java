package com.jaspersoft.cli.tool.command.common.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;

public class TreeConverterTest {

    @Test
    public void should_convert_string_to_tree() {
        TreeConverter converter = new TreeConverter();
        TreeNode treeNode = converter.toTree(asList("a/b/c", "a/b/d", "a/a/b"), "a");
        Assert.assertNotNull(treeNode);
    }
}