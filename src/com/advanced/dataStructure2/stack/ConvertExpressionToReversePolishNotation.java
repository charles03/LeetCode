package com.advanced.dataStructure2.stack;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by charles on 12/7/16.
 * Given an expression string array, return the Reverse Polish notation of this expression. (remove the parentheses)
 * Example
 For the expression [3 - 4 + 5] (which denote by ["3", "-", "4", "+", "5"]), return [3 4 - 5 +] (which denote by ["3", "4", "-", "5", "+"])
 */
public class ConvertExpressionToReversePolishNotation {
    /**
     * @param expression: A string array
     * @return: The Reverse Polish notation of this expression
     */
    /**
     * Thought:
     * Reverse Polish Notation
     * Put operator after operands
     * First Build the tree, then do post-traversal
     */
    public ArrayList<String> convertToRPN(String[] expression) {
        // write your code here
        ArrayList<String> res = new ArrayList<>();
        if (expression == null || expression.length == 0) {
            return res;
        }
        TreeNode root = buildTree(expression);
        // valid edge case
        if (root == null) {
            return res;
        }
        // post traversal tree
        postTraversal(root, res);
        return res;
    }

    public TreeNode buildTree(String[] expression) {
        Stack<TreeNode> stack = new Stack<>();
        int priorityBase = 0;
        int priorityVal = 0;

        for (int i = 0; i < expression.length; i++) {
            if ("(".equals(expression[i])) {
                priorityBase += 10;
                continue;
            }
            if (")".equals(expression[i])) {
                priorityBase -= 10;
                continue;
            }
            priorityVal = getWeight(expression[i], priorityBase);

            TreeNode node = new TreeNode(expression[i], priorityVal);
            while (!stack.isEmpty() && node.val <= stack.peek().val) {
                node.left = stack.pop();
            }
            if (!stack.isEmpty()) {
                stack.peek().right = node;
            }
            // last step add node into stack
            stack.push(node);
        }
        // check edge case
        if (stack.isEmpty()) {
            return null;
        }
        TreeNode root = stack.pop();
        while (!stack.isEmpty()) {
            root = stack.pop();
        }
        return root;
    }

    // use recursion to post traversal (left -> right -> root)
    private void postTraversal(TreeNode root, ArrayList<String> list) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            postTraversal(root.left, list);
        }
        if (root.right != null) {
            postTraversal(root.right, list);
        }
        // add string attribute of node into list
        list.add(root.str);
    }

    private int getWeight(String str, int base) {
        switch(str) {
            case "+":
            case "-":
                return base + 1;
            case "*":
            case "/":
                return base + 2;
            default:
                return Integer.MAX_VALUE;
        }
    }

    /** build expression Tree **/
    private class TreeNode {
        int val;
        String str;
        TreeNode left;
        TreeNode right;
        public TreeNode(String str, int val) {
            this.val = val;
            this.str = str;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {
        ConvertExpressionToReversePolishNotation c = new ConvertExpressionToReversePolishNotation();
        String[] exp1 = {"3", "-", "(", "4", "+", "5", "*", "1", ")"};
        System.out.println(c.convertToRPN(exp1));
        System.out.println(c.convertToRPNTestify(exp1));
        System.out.println(c.convertToRPN(exp1).equals(c.convertToRPNTestify(exp1)));
    }

    /**
     * Solution Two
     * Thought, need to verify
     * apply class with priority value and string value
     * use stack directly without tree's assistance
     */
    public ArrayList<String> convertToRPNTestify(String[] expression) {
        ArrayList<String> res = new ArrayList<>();
        if (expression == null || expression.length == 0) {
            return res;
        }
        int priorityBase = 0;
        int priorityValue = 0;
        Stack<Node> stack = new Stack<>();
        for (int i = 0; i < expression.length; i++) {
            if ("(".equals(expression[i])) {
                priorityBase += 10;
                continue;
            }
            if (")".equals(expression[i])) {
                priorityBase -= 10;
                continue;
            }
            priorityValue = getWeight(expression[i], priorityBase);
            Node node = new Node(expression[i], priorityValue);

            while (!stack.isEmpty() && node.priority <= stack.peek().priority) {
                res.add(stack.pop().symbol);
            }
            stack.push(node);
        }
        if (stack.isEmpty()) {
            return res;
        }
        while (!stack.isEmpty()) {
            res.add(stack.pop().symbol);
        }
        return res;
    }

    private class Node {
        int priority;
        String symbol;
        public Node(String symbol, int priority) {
            this.symbol = symbol;
            this.priority = priority;
        }
    }

}
