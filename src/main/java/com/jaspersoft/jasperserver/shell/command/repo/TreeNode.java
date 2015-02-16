package com.jaspersoft.jasperserver.shell.command.repo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

import static java.lang.System.out;

/**
 * Resource tree representation class.
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

    private void print(String prefix, Boolean isTail) {
        out.println(prefix + (isTail ? "\u2514\u2500\u2500 " : "\u251c\u2500\u2500 ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "\u2502   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ? "    " : "\u2502   "), true);
        }
    }
}
