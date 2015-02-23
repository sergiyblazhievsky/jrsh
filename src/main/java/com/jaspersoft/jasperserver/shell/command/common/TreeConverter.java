package com.jaspersoft.jasperserver.shell.command.common;

import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;
import static java.util.Arrays.asList;
import static java.util.regex.Pattern.quote;

/**
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
public class TreeConverter {

    public TreeNode toTree(List<String> arr, String path) {
        TreeNode root = new TreeNode("\u2510                                              ", new ArrayList<TreeNode>());
        TreeNode tempRoot = root;
        for (String line : arr) {
            List<String> lines = convert(line);
            for (String val : lines) {
                List<TreeNode> children = tempRoot.getChildren();
                if (children != null) {
                    if (!children.contains(new TreeNode(val, new ArrayList<TreeNode>()))) {
                        TreeNode tempNode = new TreeNode(val, new ArrayList<TreeNode>());
                        children.add(tempNode);
                        tempRoot = tempNode;
                    } else {
                        tempRoot = children.get(children.indexOf(new TreeNode(val, new ArrayList<TreeNode>())));
                    }
                }
            }
            tempRoot = root;
        }
        List<String> givenPath = convert(path);
        TreeNode from = root;
        for (String nodeName : givenPath) {
            TreeNode node = new TreeNode(nodeName, new ArrayList<TreeNode>());
            if (from.getChildren().contains(node)) {
                from = from.getChildren().get(from.getChildren().indexOf(node));
            }
        }
        return from;
    }

    private List<String> convert(String s) {
        if (s.startsWith("/") && s.endsWith("/")) {
            s = s.substring(1, s.length() - 1);
        }
        if (s.startsWith("/")) {
            s = s.substring(1);
        }
        if (s.endsWith("/")) {
            s = s.substring(0, s.length() - 1);
        }
        return asList(s.split(quote(separator)));
    }
}
