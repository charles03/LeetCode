package com.leetcode.backtrack;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 1/20/17.
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

 For example, given n = 3, a solution set is:
 [
 "((()))",
 "(()())",
 "(())()",
 "()(())",
 "()()()"
 ]
 */
public class GenerateParentheses_22 {
    private static String OPEN = "(";
    private static String CLOSE = ")";
    public List<String> generateParenthesis(int n) {
        List<String> res = new LinkedList<>();
        backTrack(res, "", 0, 0, n);
        return res;
    }

    private void backTrack(List<String> res, String str, int openIdx, int closeIdx, int max) {
        if (str.length() == max * 2) {
            res.add(str);
            return;
        }
        if (openIdx < max) {
            backTrack(res, str + OPEN, openIdx + 1, closeIdx,  max);
        }
        if (closeIdx < openIdx) {
            backTrack(res, str + CLOSE, openIdx, closeIdx + 1, max);
        }
    }
}
