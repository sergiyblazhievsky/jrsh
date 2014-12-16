package com.jaspersoft.cli.tool.command.common.tree;

import java.util.List;

/**
 * Resource Tree representation class. It holds a tree of JRS resources.
 * @author Alex Krasnyanskiy
 * @since 1.0
 */
//@Data
//@AllArgsConstructor
public class TreeNode {


    /**
     * Name of the Node
     */
    //private String name;

    /**
     * Children nodes.
     */
    //private List<TreeNode> children;

    /**
     * Init tree building method.
     */
    //public void print() {
    //    print("", true);
    //}

    /**
     * Print JRS resources tree recursively.
     * @param prefix used for building a visual structure of the tree
     * @param isTail flag which indicates is our has children (tail)
     */
    /*
    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ? "    " : "│   "), true);
        }
    }
    */
    private String name;
    private List<TreeNode> children;

    public TreeNode(String name, List<TreeNode> children) {
        this.name = name;
        this.children = children;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeNode)) return false;
        TreeNode treeNode = (TreeNode) o;
        return !(name != null
                ? !name.equals(treeNode.name)
                : treeNode.name != null);
    }

    @Override
    public int hashCode() {
        return name != null
                ? name.hashCode()
                : 0;
    }
}
