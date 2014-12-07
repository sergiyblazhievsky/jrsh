package com.jaspersoft.cli.tool.core.tree;

import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public interface IConverter {
    TreeNode toTree(List<String> arr);
    TreeNode toTree(List<String> arr, String path);
    List<String> convert(String s);
}
