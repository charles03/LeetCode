package com.leetcode.divideconquer;

import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 2/19/17.
 * Given a string that contains only digits 0-9 and a target value, return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

 Examples:
 "123", 6 -> ["1+2+3", "1*2*3"]
 "232", 8 -> ["2*3+2", "2+3*2"]
 "105", 5 -> ["1*0+5","10-5"]
 "00", 0 -> ["0+0", "0-0", "0*0"]
 "3456237490", 9191 -> []
 */
public class ExpressionAddOperators_282 {
    /**
     * divide conquer solution
     * another solution is in link https://discuss.leetcode.com/topic/27285/some-thoughts-on-the-algorithm-slr-1-and-optimization-meet-in-the-middle-spatial-data-structure
     */
    public List<String> addOperators(String nums, int target) {
        List<String> res = new LinkedList<>();
        if (nums.length() == 0) {
            return res;
        }
        int len = nums.length();
        char[] fullPath = new char[2 * len - 1];
        char[] digits = nums.toCharArray();
        long num = 0;
        for (int i = 0; i < digits.length; i++) {
            num = num * 10 + digits[i] - '0';
            fullPath[i] = digits[i];
            dfs(res, digits, fullPath, 0, num, i + 1, i + 1, target);
            if (num == 0) { // not allow number with 0 prefix, except zero itself
                break;
            }
        }
        return res;
    }

    /**
     * based on priority of operator, expression can be only format left + right == target
     * both left and right may calculated with multiply or negative sign
     */
    private void dfs(List<String> res, char[] digits, char[] path, long opLeft, long opRight, int opIndex, int pos, int target) {
        System.out.println(String.format("%d, %d, %d, %d", opLeft, opRight, opIndex, pos));
        // return case
        if (pos == digits.length && opLeft + opRight == target) {
            res.add(new String(path, 0, opIndex));
        }
        long curr = 0;
        int j = opIndex + 1;
        for (int i = pos; i < digits.length; i++) {
            curr = curr * 10 + digits[i] - '0';
            path[j] = digits[i];
            path[opIndex] = '+';
            dfs(res, digits, path, opLeft + opRight, curr, j+1, i+1, target);

            path[opIndex] = '-';
            dfs(res, digits, path, opLeft + opRight, -curr, j+1, i+1, target);

            path[opIndex] = '*';
            // multiply has higher priority
            dfs(res, digits, path, opLeft, opRight * curr, j+1, i+1, target);

            if (digits[pos] == '0') {
                break;
            }
        }
    }

    public static void main(String[] args) {
        ExpressionAddOperators_282 e = new ExpressionAddOperators_282();
        String[] arr = {"123", "232", "105", "00"};
        int[] targets = {6,8,5,0};

        for (int i = 0; i < arr.length; i++) {
            output(e.addOperators(arr[i], targets[i]));
        }
    }

    private static void output(List<String> res) {
        res.stream().forEach(t -> System.out.println(t));
    }
}
