package com.leetcode.stack;

/**
 * Created by charles on 3/16/17.
 * Given a nested list of integers represented as a string, implement a parser to deserialize it.

 Each element is either an integer, or a list -- whose elements may also be integers or other lists.

 Note: You may assume that the string is well-formed:

 String is non-empty.
 String does not contain white spaces.
 String contains only digits 0-9, [, - ,, ].
 Example 1:

 Given s = "324",

 You should return a NestedInteger object which contains a single integer 324.
 Example 2:

 Given s = "[123,[456,[789]]]",

 Return a NestedInteger object containing a nested list with 2 elements:

 1. An integer containing value 123.
 2. A nested list containing two elements:
 i.  An integer containing value 456.
 ii. A nested list with one element:
 a. An integer containing value 789.
 */

import javax.jnlp.IntegrationService;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class MiniParser_385 {
    /**
     * The idea is very straightforward:
     if it's '[', we just construct a new nested integer and push it onto the stack
     if it's a number, we parse the whole number and add to the previous nested integer object
     if it's ',', we'll just continue;
     if it's ']', we'll just pop one nested integer from the working stack and assign it to the result
     Also, we'll pay attention to this corner case or understand the input: the input could be "324", "[324]", they are different: the former should return a nested integer with one single integer, the latter should return a nested integer with a list
     */
    public NestedInteger deserialize(String s) {
        if (s == null || s.isEmpty()) {
            return new NestedInteger();
        }
        Stack<NestedInteger> stack = new Stack<>();
        int sign = 1, len = s.length();
        char[] chars = s.toCharArray();

        for (int i = 0; i < len; i++) {
            char c = chars[i];
            if (c == '[') {
                stack.push(new NestedInteger());
            } else if (c == ']' && stack.size() > 1) {
                // end of a nested integer
                NestedInteger n = stack.pop();
                stack.peek().add(n);
            } else if (c == '-') {
                // just change sign
                sign = -1;
            } else if (Character.isDigit(c)) {
                // if digit check for all continuous digits
                int num = c - '0';
                while (i + 1 < len && Character.isDigit(chars[i+1])) {
                    num = num * 10 + chars[i+1] - '0';
                    i++;
                }
                num *= sign; // append sign
                if (!stack.isEmpty()) {
                    // add to previous item if not empty
                    stack.peek().add(new NestedInteger(num));
                } else {
                    stack.push(new NestedInteger(num));
                }
                sign = 1; // reset sign
            }
        }
        if (stack.isEmpty()) {
            return new NestedInteger();
        }
        return stack.pop();
    }
    /** Stack solution version II */
    public NestedInteger deserializeIII(String s) {
        if (!s.startsWith("[")) {
            return new NestedInteger(Integer.valueOf(s));
        }
        Stack<NestedInteger> stack = new Stack<>();
        NestedInteger res = new NestedInteger();
        stack.push(res);
        int start = 1;
        char[] chars = s.toCharArray();
        for (int i = 1; i < s.length(); i++) {
            char c = chars[i];
            if (c == '[') {
                NestedInteger n = new NestedInteger();
                stack.peek().add(n);
                stack.push(n);
                start = i + 1;
            } else if (c == ',' || c == ']') {
                if (i > start) {
                    Integer val = Integer.valueOf(new String(chars, start, i));
                }
                start = i + 1;
                if (c == ']') {
                    stack.pop();
                }
            }
        }
        return res;
    }

    /** recursion without optimization*/
    public NestedInteger deserializeII(String s) {
        NestedInteger res = new NestedInteger();
        if (s == null || s.length() == 0) {
            return res;
        }
        if (s.charAt(0) != '[') {
            res.setInteger(Integer.parseInt(s));
        } else if (s.length() > 2) {
            int start = 1, count = 0;
            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);
                if (count == 0 && (c == ',' || i == s.length() - 1)) {
                    res.add(deserializeII(s.substring(start, i)));
                    start = i + 1;
                } else if (c == '[') {
                    count++;
                } else if (c == ']') {
                    count--;
                }
            }
        }
        return res;

    }

}

class NestedInteger {
    private List<NestedInteger> list;
    private Integer num;

    NestedInteger(List<NestedInteger> list) {
        this.list = list;
    }
    NestedInteger(Integer num) {
        this.num = num;
    }
    NestedInteger() {
        this.list = new ArrayList<>();
    }
    void add (NestedInteger num) {
        if (this.list != null) {
            this.list.add(num);
        } else {
            this.list = new ArrayList<>();
            this.list.add(num);
        }
    }
    void setInteger(int num) {
        this.num = num;
    }
    boolean isInteger() {
        return num != null;
    }
    Integer getInteger() {
        return num;
    }
    List<NestedInteger> getList() {
        return list;
    }
    String printNestedInteger(NestedInteger n, StringBuilder sb) {
        if (n.isInteger()) {
            sb.append(n.num).append(",");
        }
        sb.append("[");
        for (NestedInteger nest : n.list) {
            if (nest.isInteger()) {
                sb.append(nest.num).append(",");
            } else {
                printNestedInteger(nest, sb);
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
