package com.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by charles on 12/22/16.
 */
public class TreeNodeUtil {
    // encode a tree to a single string
    /**
     * This method will be invoked first, you should design your own algorithm
     * to serialize a binary tree which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public static String serialize(TreeNode root) {
        if (root == null) {
            return "{}";
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size = 0;
        TreeNode node = null;
        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            size = queue.size();
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                if (node == null) {
                    sb.append("#,");
                    continue;
                } else {
                    sb.append(node.val).append(",");
                }
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        return sb.toString();
    }
    /** decodes encoded data to tree */
    public static TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        if (data.equals("{}")) {
            return null;
        }
        String[] vals = data.split(",");
        List<TreeNode> list = new ArrayList<>();
        int index = 0;
        boolean isLeftChild = true;
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        list.add(root);
        TreeNode node = null;

        for (int i = 1; i < vals.length; i++) {
            if (!vals[i].equals("#")) {
                node = new TreeNode(Integer.parseInt(vals[i]));
                if (isLeftChild) {
                    list.get(index).left = node;
                } else {
                    list.get(index).right = node;
                }
                list.add(node);
            }
            if (!isLeftChild) { // no matter null node, root has left and right
                index++;
            }
            // rotating
            isLeftChild = !isLeftChild;
        }
        return root;
    }
}
