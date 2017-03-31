package com.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by charles on 1/3/17.
 * Given a binary tree, return all root-to-leaf paths.
 For example, given the following binary tree:
    1
  /   \
 2     3
  \
   5
 All root-to-leaf paths are:

 ["1->2->5", "1->3"]
 */
public class BinaryTreePaths_257 {
    public List<String> binaryTreePathsRecursion(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root != null) {
            searchTreePath(root, "", res);
        }
        return res;
    }

    private void searchTreePath(TreeNode root, String path, List<String> paths) {
        // case to add one path into list
        if (root.left == null && root.right == null) {
            paths.add(path + root.val);
        }
        // case to search left path
        if (root.left != null) {
            searchTreePath(root.left, path + root.val + "->", paths);
        }
        // case to search right path
        if (root.right != null) {
            searchTreePath(root.right, path + root.val + "->", paths);
        }
    }

    public List<String> binaryTreePathsIteration(TreeNode root) {
        List<String> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        Stack<Wrapper> stack = new Stack<>();
        stack.add(new Wrapper(root, "" + root.val));

        Wrapper wrap = null;
        while (!stack.isEmpty()) {
            wrap = stack.pop();
            if (wrap.node.left == null && wrap.node.right == null) {
                res.add(wrap.path);
            }
            if (wrap.node.left != null) {
                stack.add(new Wrapper(wrap.node.left, wrap.path + "->" + wrap.node.left.val));
            }
            if (wrap.node.right != null) {
                stack.add(new Wrapper(wrap.node.right, wrap.path + "->" + wrap.node.right.val));
            }
        }
        return res;
    }

    private class Wrapper {
        private TreeNode node;
        private String path;

        public Wrapper(TreeNode node, String path) {
            this.node = node;
            this.path = path;
        }
    }

    public static void main(String[] args) {
        BinaryTreePaths_257 b = new BinaryTreePaths_257();
        TreeNode root = TreeNodeUtil.deserialize("1,2,3,#,5");
        output(b.binaryTreePathsIteration(root));
    }

    private static void output(List<String> list) {
        list.stream().forEach(System.out::println);
    }
}
