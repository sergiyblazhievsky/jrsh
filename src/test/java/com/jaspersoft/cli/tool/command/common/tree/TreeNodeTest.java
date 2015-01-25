package com.jaspersoft.cli.tool.command.common.tree;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.PowerMockitoCore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.testng.Assert.*;

@PrepareForTest({TreeNode.class})
public class TreeNodeTest extends PowerMockitoCore {

    @Test
    public void should_print_tree() throws Exception {
        PowerMockito.suppress(PowerMockito.method(TreeNode.class, "print", String.class, Boolean.class));
        TreeNode root = Mockito.spy(new TreeNode("root", Collections.<TreeNode>emptyList()));
        root.print();

        PowerMockito.verifyPrivate(root).invoke("print", "root", true);
    }
}