package com.advanced.dataStructure2.stack;

import java.util.Stack;

/**
 * Created by charles on 12/4/16.
 * The structure of Expression Tree is a binary tree to evaluate certain expressions.
 All leaves of the Expression Tree have an number string value. All non-leaves of the Expression Tree have an operator string value.
 Now, given an expression array, build the expression tree of this expression, return the expTreeNode of this expression tree.
 Example
 For the expression (2*6-(23+7)/(1+2)) (which can be represented by ["2" "*" "6" "-" "(" "23" "+" "7" ")" "/" "(" "1" "+" "2" ")"]).
 The expression tree will be like

                [ - ]
            /          \
     [ * ]                 [ / ]
    /     \             /         \
 [ 2 ]  [ 6 ]       [ + ]        [ + ]
                    /    \       /      \
                [ 23 ][ 7 ] [ 1 ]   [ 2 ] .
 After building the tree, you just need to return expTreeNode node [-]
 */
public class ExpressionTreeBuild {
    /**
     * Thought: Similar to {@link //MaxTree}
     * to build MinTree, expTreeNode is smallest, but rest of logic is same as MaxTree
     * For assistance of keeping order of ExpressionTreeNode, add weight/priority
     * on ExpressionTreeNode, wrapped into TreeNode
     *
     * assign corresponding priority to each operator/num/sign
     * each symbol has own base.
     * base will be updated plus 10 when meet "("
     * or minus 10 when meet ")"
     * "+" or "-" base + 1
     * "*" or "/" base + 2
     * higher base is higher priority which should be more close to bottom
     *
     * How to build tree similar to MinTree or Max Tree question
     * Do not add symbol "(" and ")" into tree
     */
    public ExpressionTreeNode buildExpressionTree(String[] expressions) {
        if (expressions == null || expressions.length == 0) {
            return new ExpressionTreeNode("");
        }

        Stack<TreeNode> stack = new Stack<>(); // Tree node is weight based ExpressionTreeNode
        int priorityBase = 0; // to define priority
        int priorityVal = 0; // weight of each symbol, num should be MAX_VALUE
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i].equals("(")) { // add priority base but not to add symbol into tree
                priorityBase += 10;
                continue;
            }
            if (expressions[i].equals(")")) { // same to case "("
                priorityBase -= 10;
                continue;
            }
            priorityVal = getWeight(expressions[i], priorityBase);

            TreeNode node = new TreeNode(expressions[i], priorityVal);
            while (!stack.isEmpty() && node.priority <= stack.peek().priority) {
                // if priority of current node is smaller than existing in stack
                // assign stack.peek to left child of current node
                System.out.println(String.format("current node %s with weight %s and left child %s", expressions[i], priorityVal, stack.peek().expTreeNode.symbol));
                node.expTreeNode.left = stack.pop().expTreeNode;
            }
            // after pop, update current node tree structure
            // new node is the right child of peek node in stack
            // because priority of peek after pop process is small than priority of current node
            if (!stack.isEmpty()) {
                System.out.println(String.format("right child of %s is %s", stack.peek().expTreeNode.symbol, node.expTreeNode.symbol));
                stack.peek().expTreeNode.right = node.expTreeNode;
            }
            stack.push(node); // add node into stack
        }
        // stack should not be empty if should have valid number as base
        if (stack.isEmpty()) {
            return null;
        }
        // final returned node is the node in the bottom of stack
        // thus pop out all nodes in stack, only return the last one
        TreeNode finalRoot = stack.pop();
        while (!stack.isEmpty()) {
            finalRoot = stack.pop();
            System.out.println("remained in stack " + finalRoot.expTreeNode.symbol);
        }
        return finalRoot.expTreeNode;
    }

    /**
     * method implemented by jiuzhang,
     * consider edge case in same for loop
     */
    public ExpressionTreeNode buildTreeByJiuzhang(String[] expressions) {
        Stack<TreeNode> stack = new Stack<>();
        int priorityBase = 0;
        int priorityVal = 0;
        TreeNode node = null;
        TreeNode popped = null;
        TreeNode remained = null;

        for (int i = 0; i <= expressions.length; i++) {
            if (i != expressions.length) { // normal case, no array out index
                if (expressions[i].equals("(")) {
                    priorityBase += 10;
                    continue;
                }
                if (expressions[i].equals(")")) {
                    priorityBase -= 10;
                    continue;
                }
                priorityVal = getWeight(expressions[i], priorityBase);
            }
            // instantiate tree node
            if (i != expressions.length) {
                node = new TreeNode(expressions[i], priorityVal);
            } else { // assign minmum prioprity to extra var
                node = new TreeNode("", Integer.MIN_VALUE);
            }
            // loop stack
            while (!stack.isEmpty() && node.priority <= stack.peek().priority) {
                popped = stack.pop();
                if (stack.isEmpty()) { // left node if no node in stack
                    node.expTreeNode.left = popped.expTreeNode;
                } else {
                    /**
                     * based on principle of proximity
                     * connect node of popped to the node which priority is more closer
                     */
                    remained = stack.peek();
                    if (remained.priority < node.priority) {
                        // priority of current node is closer to priority of popped
                        // so connect popped as left child of current nodes
                        System.out.println(String.format("remain %s, current node %s, pop %s, left", remained.expTreeNode.symbol, node.expTreeNode.symbol, popped.expTreeNode.symbol));
                        node.expTreeNode.left = popped.expTreeNode;
                    } else {
                        // priority of remained is closer to priority of popped
                        // so connect popped as right child of remained
                        System.out.println(String.format("remain %s, current node %s, pop %s, right", remained.expTreeNode.symbol, node.expTreeNode.symbol, popped.expTreeNode.symbol));
                        remained.expTreeNode.right = popped.expTreeNode;
                    }
                }
            }
            stack.push(node);
        }
        return stack.peek().expTreeNode.left;
    }

    /**
     * To return weight of each symbol, based on current priority base
     * for case "+" or "-", plus one in priority base
     * for case "*" or "/", plus two in priority base
     * so as to differentiate weight
     */
    private int getWeight(String str, int priorityBase) {
        if (str.equals("+") || str.equals("-")) {
            return priorityBase + 1;
        }
        if (str.equals("*") || str.equals("/")) {
            return priorityBase + 2;
        }
        return Integer.MAX_VALUE; // for numbers
    }

    public static void main(String[] args) {
        ExpressionTreeBuild build = new ExpressionTreeBuild();
        String[] expressions = {"2", "*", "6", "-", "(", "23", "+", "7", ")", "/", "(", "1", "+", "2", ")"};
        ExpressionTreeNode root = build.buildExpressionTree(expressions);
//        ExpressionTreeNode root = build.buildTreeByJiuzhang(expressions);
        printTree(root);
    }

    private static void printTree(ExpressionTreeNode root) {
        Stack<ExpressionTreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            ExpressionTreeNode node = stack.pop();
            System.out.print(node.symbol + ",");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }
}

/**
 * define TreeNode class with int value as priority
 */
class TreeNode {
    public int priority;
    public int str;
    public ExpressionTreeNode expTreeNode;

    public TreeNode(String str, int priority) {
        this.priority = priority;
        this.expTreeNode = new ExpressionTreeNode(str);
    }
}

class ExpressionTreeNode {
    public String symbol;
    public ExpressionTreeNode left, right;
    public ExpressionTreeNode(String symbol) {
        this.symbol = symbol;
        this.left = null;
        this.right = null;
    }
}
