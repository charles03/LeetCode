package com.leetcode.stack;

import java.util.*;

/**
 * Created by charles on 4/9/17.
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

 The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 */
public class ValidParentheses_20 {
    char[] openBracket = {'(', '{', '['};
    char[] closeBracket = {')', '}', ']'};

    public boolean isValid(String s) {
        Set<Character> open = new HashSet<>(Arrays.asList('(', '{', '['));
        Set<Character> close = new HashSet<>(Arrays.asList(')', '}', ']'));
        Map<Character, Character> map = buildMap();

        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (open.contains(c)) {
                stack.push(c);
            } else if (close.contains(c)) {
                // need to check empty of stack before call function of stack
                if (stack.isEmpty() || !map.get(c).equals(stack.pop())) {
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            // if there are still open bracket in stack
            return false;
        }
        return true;
    }
    private Map<Character, Character> buildMap() {
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < closeBracket.length; i++) {
            map.put(closeBracket[i], openBracket[i]);
        }
        return map;
    }

    public static void main(String[] args) {
        /** edge cases */
        ValidParentheses_20 v = new ValidParentheses_20();
        String s1 = "]";
        System.out.println(v.isValid(s1) == false);
        String s2 = "[";
        System.out.println(v.isValid(s2) == false);
    }

    public boolean isValidII(String s) {
        char[] stack = new char[s.length()];
        int head = 0;
        for (char c : s.toCharArray()) {
            switch (c) {
                case '{':
                case '[':
                case '(':
                    stack[head++] = c;
                    break;
                case '}':
                    if (head == 0 || stack[--head] != '{') {
                        return false;
                    }
                    break;
                case ']':
                    if (head == 0 || stack[--head] != '[') {
                        return false;
                    }
                    break;
                case ')':
                    if (head == 0 || stack[--head] != '(') {
                        return false;
                    }
                    break;
            }
        }
        return head == 0;
    }
}
