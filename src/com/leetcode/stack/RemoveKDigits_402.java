package com.leetcode.stack;

import java.util.Stack;

/**
 * Created by charles on 11/12/16.
 * Given a non-negative integer num represented as a string,
 * remove k digits from the number so that the new number is the smallest possible.
 * Noted:
 * The length of num is less than 10002 and will be ≥ k. The given num does not contain any leading zero.
 * Example 1:

 Input: num = "1432219", k = 3
 Output: "1219"
 Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
 Example 2:

 Input: num = "10200", k = 1
 Output: "200"
 Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
 Example 3:

 Input: num = "10", k = 2
 Output: "0"
 Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 */
public class RemoveKDigits_402 {
    /**
      Idea: scan the array of digits from left to right, push into stack
        During scan, pop elements until current digit is not less than peek of stack
        Popped digits are removed, then push current digit into stack
      Noted:
        -- digits in stack are always in (non-strictly) increasing order
        -- do not remove more than k digits, stop scan when necessary
     */
    public String removeKdigits(String num, int k) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 0; i < num.length(); i++) {
            int digit = num.charAt(i) - '0';
            while (!stack.isEmpty() && stack.peek() > digit && k > 0) {
                stack.pop();
                k--;
            }
            stack.push(digit);
        }
        // use up all k
        while (k > 0) {
            stack.pop();
            k--;
        }
        // left append from stack
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }
        return trimLeadingZero(sb.toString());
    }
    private String trimLeadingZero(String sb) {
        int index = 0;
        while (index < sb.length() && sb.charAt(index) == '0') {
            index++;
        }
        if (index == sb.length()) {
            return "0";
        }
        // substring from start index
        return sb.toString().substring(index);
    }

    @Deprecated
    public String removeKdigitsInFailure(String numStr, int k) {
        if (numStr == null || numStr.length() == 0) {return "";}
        int len = numStr.length();
        if (len <= k) {return "0";}

        int stackSize = len - k;
        // only conside number string length larger than k
        char[] stack = new char[stackSize];
        for (int i = stackSize - 1; i >= 0; i--) {
            stack[i] = numStr.charAt(i + k);
        }
        // rest of digit from string, either push into stack or ignore
        for (int j = k - 1; j >= 0; j--) {
            if (numStr.charAt(j) < stack[0]) {
                stack[0] = numStr.charAt(j);
            }
        }
        // ignore leading zero
        int j = 0;
        while (j < stackSize && stack[j] == '0') {
            j++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = j; i < stackSize; i++) {
            sb.append(stack[i]);
        }
        String res = sb.toString();
        if (res.equals("")) {
            return "0";
        }
        return res;
    }

    public static void main(String[] args) {
        RemoveKDigits_402 r = new RemoveKDigits_402();
        String num = "1432219";
        String num1 = "10";
        String num2 = "112";
        System.out.println(r.removeKdigits(num, 3));
        System.out.println(r.removeKdigits(num1, 1));
        System.out.println(r.removeKdigits(num2, 1)  == "11");
    }
}
