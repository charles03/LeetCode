package com.leetcode.divideconquer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by charles on 2/12/17.
 * Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. The valid operators are +, - and *.
 * Example 1
 Input: "2-1-1".

 ((2-1)-1) = 0
 (2-(1-1)) = 2
 Output: [0, 2]

 Example 2
 Input: "2*3-4*5"

 (2*(3-(4*5))) = -34
 ((2*3)-(4*5)) = -14
 ((2*(3-4))*5) = -10
 (2*((3-4)*5)) = -10
 (((2*3)-4)*5) = 10
 Output: [-34, -14, -10, -10, 10]
 */
public class DifferentWaysToAddParentheses_241 {
    public List<Integer> diffWaysToCompute(String input) {
        Map<String, List<Integer>> cache = new HashMap<>();
        return diffWaysToCompute(input, cache);
    }

    /**
     * In order to avoid solving same sub problem repeatedly,
     * use memory cache to store previous results,
     * Because it applies bottom-up recursive, always can store result of sub problem and reuse
     */
    public List<Integer> diffWaysToCompute(String input, Map<String, List<Integer>> cache) {
        if (cache.containsKey(input)) {
            return cache.get(input);
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char operator = input.charAt(i);
            if (operator == '+' || operator == '-' || operator == '*') {
                List<Integer> leftExpression = diffWaysToCompute(input.substring(0, i), cache);
                List<Integer> rightExpression = diffWaysToCompute(input.substring(i+1), cache);

                // apply traverse, give res as parameter into method
                // otherwise only single result return in list
                generateResult(res, operator, leftExpression, rightExpression);
            }
        }
        // when case no operator
        if (res.isEmpty()) {
            if (input == null || input.length() == 0) {
                res.add(0);
            } else {
                res.add(Integer.valueOf(input));
            }
        }
        // store prev res into cache
        cache.put(input, res);
        return res;
    }

    private void generateResult(List<Integer> res, char operator, List<Integer> left, List<Integer> right) {
        for (int x : left) {
            for (int y : right) {
                if (operator == '+') {
                    res.add(x + y);
                }
                if (operator == '-') {
                    res.add(x - y);
                }
                if (operator == '*') {
                    res.add(x * y);
                }
            }
        }
    }

    /**
     * TODO .. pending to finish
     */
    public List<Integer> diffWaysToComputeByDp(String input) {
        String[] digits = splitStr(input);
        int size = digits.length - 1;
        String[] ops = collectOps(input, size - 1);
        return diffWaysToComputeByDp(digits, ops, 0, size);
    }

    public List<Integer>  diffWaysToComputeByDp(String[] digits, String[] ops, int l, int r) {
        for (int i = l; i <= r; i++) {
            List<Integer> left = diffWaysToComputeByDp(digits, ops, l, i);
            List<Integer> right = diffWaysToComputeByDp(digits, ops, i+1, r);
        }
        return new ArrayList<>();
    }


    public String[] splitStr(String input) {
        Pattern p = Pattern.compile("[-+*]");
        String[] res = input.split("[-+*]");
        for (int i = 0; i < res.length; i++) {
            if (res[i].length() == 0) {
                res[i] = "0";
            }
        }
        return res;
    }
    private String[] collectOps(String input, int size) {
        String[] ops = new String[size];
        int idx = 0;
        for (char c : input.toCharArray()) {
            if (c == '+' || c == '+' || c == '*') {
                ops[idx] = String.valueOf(c);
            }
        }
        return ops;
    }

    public static void main(String[] args) {
        DifferentWaysToAddParentheses_241 d = new DifferentWaysToAddParentheses_241();
        String s1 = "2*3-4*5";
        String s2 = "-2*3-4*5";
        printRes(d.diffWaysToCompute(s1));
        printRes(d.diffWaysToCompute(s2));
        d.splitStr(s1);
    }

    private static void printRes(List<Integer> res) {
        // should use string "," instead of char ','
        res.stream().forEach(t -> System.out.print(t + ","));
        System.out.println();
    }


}
