package com.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by charles on 12/23/16.
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer,
 * or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

 Design an algorithm to serialize and deserialize a binary search tree.
 There is no restriction on how your serialization/deserialization algorithm should work.
 You just need to ensure that a binary search tree can be serialized to a string and this string can be deserialized to the original tree structure.

 The encoded string should be as compact as possible.
 Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
 */
public class SerializeAndDeserializeBST_449 {
    // Your Codec object will be instantiated and called as such:
    // Codec codec = new Codec();
    // codec.deserialize(codec.serialize(root));
    /**
     * This method will be invoked first, you should design your own algorithm
     * to serialize a binary tree which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "{}";
        }
        /** apply pre-order to serialize tree */
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size = 0;
        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
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

    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null; // should throw exception
        }
        if (data.equals("{}")) {
            return null;
        }
        String[] vals = data.split(",");

        List<TreeNode> list = new ArrayList<>();
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        list.add(root);

        int index = 0;
        boolean isLeftChild = true;
        for (int i = 1; i < vals.length; i++) {
            if (!vals[i].equals("#")) {
                TreeNode node = new TreeNode(Integer.parseInt(vals[i]));
                if (isLeftChild) {
                    list.get(index).left = node;
                } else {
                    list.get(index).right = node;
                }
                list.add(node);
            }
            if (!isLeftChild) {
                index++;
            }
            // rotating state of isLeftChild
            isLeftChild = !isLeftChild;
        }

        return root;
    }

    public static void main(String[] args) {
        SerializeAndDeserializeBST_449 s = new SerializeAndDeserializeBST_449();
        System.out.println(s.serialize(createTestTree()));
        String tree = "1,#,2,3,4,#,#,#,5";
        System.out.println(s.serialize(s.deserialize(tree)));

        int x = -1;
        x >>>= 1;
        System.out.println(x);

    }

    public static TreeNode createTestTree() {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);

        n1.right = n2;
        n2.left = n3;
        n2.right = n4;
        n4.right = n5;

        return n1;
    }

}
