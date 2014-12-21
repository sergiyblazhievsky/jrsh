package com.jaspersoft.cli.tool.command.common.tree;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

import static java.lang.System.out;

/**
 * Resource Tree representation class.
 */
@EqualsAndHashCode(exclude = {"children"})
public class TreeNode {

    @Getter
    private String name;
    @Getter
    private List<TreeNode> children;

    public TreeNode(String name, List<TreeNode> children) {
        this.name = name;
        this.children = children;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        out.println(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
