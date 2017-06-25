package com.leetcode.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by charles on 4/28/17.
 * Given an integer n, find the closest integer (not including itself), which is a palindrome.

 The 'closest' is defined as absolute difference minimized between two integers.

 Example 1:
 Input: "123"
 Output: "121"
 Note:
 The input n is a positive integer represented by string, whose length will not exceed 18.
 If there is a tie, return the smaller one as answer.
 */
public class FindClosestPalindrome_564 {
    /**
     * better solution Thought:
     * take left half of original string
     * attempt five cases, and assemble palindrome value, and then keep min-diff of original val;
     * 1. (lefthalf-1)reverse(lefthalf-1): example 1323 -> 1221
     * 2. (lefthalf-1) * 10 + 9; // handle 1, 100,1000... 10000->999999
     * 3. (lefthalf) -> example 1323 -> 1331
     * 4. (lefthalf+1) -> example 1323 -> 1441
     * 5. (lefthalf+1)/10 -> to handle 9,99,999
     *
     * return mindiff(source, 1,2,3,4,5);
     */
    public String nearestPalindromicII(String n) {
        long left = Long.parseLong(n.substring(0, (n.length()+1)/2));
        List<String> candidates = new ArrayList<>();
        concatCandidates(left-1, candidates);
        concatCandidates((left-1) * 10 + 9, candidates); //handle 10,100
        concatCandidates(left, candidates);
        concatCandidates(left+1, candidates);
        concatCandidates((left+1)/10, candidates); // handle 9,99

        return findNearest(n, candidates);
    }
    private String findNearest(String str, List<String> candidates) {
        long val = Long.parseLong(str);
        long tmp = 0, tmpDiff = 0;
        long res = 0, diff = Long.MAX_VALUE;
        System.out.println(diff);
        for (String cand : candidates) {
            try {
                tmp = Long.parseLong(cand);
            } catch (NumberFormatException e) {
                // eat exception;
                // long max is 9223372036854775807
                // generated string may over long max
            }
            if (tmp == val) {
                continue;
            }
            tmpDiff = Math.abs(tmp - val);
            if (tmpDiff < diff
                    || tmpDiff == diff && tmp < res) {
                res = tmp;
                diff = tmpDiff;
            }
        }
        return String.valueOf(res);
    }
    private void concatCandidates(long left, List<String> candidates) {
        String str = String.valueOf(left);
        System.out.println(str);
        String reverse = new StringBuilder(str).reverse().toString();
        candidates.add(str + reverse); // abc -> abccba
        candidates.add(str + reverse.substring(1)); // abc -> abcba
    }

    /**
     * point 1: convert half into palindrome by replicating half of string to the other side.
     *          we should replicate higher digits to lower digits instead vice versa.
     *          becuase "abcde" -> edcde, absolute diff |10000(a-e) + 1000(b-d)|
     *          much bigger than -> abcbc, absolute diff |10(d-b) + e-a|
     * point 2: as for special digits 0 and 9, if in the middle,
     *    decrementing 0 and incrementing 9 can lead to change in the rest of digits towards to left
     *    example : 200001 -> 200 -> 199|991
     *              10987 -> 109 -> 110|011
     */
    private long diff1;
    private long diff2;
    private long diff3;
    public String nearestPalindromicIII(String n) {
        if (n.equals("1")) {
            return "0";
        }
        /**
         * use instance variable to get updated absolute diff of number
         * between generated palindrome and original given number
         */
        String a = defaultPalindrome(n);
        String b = handleZeroForPalindrome(n);
        String c = handleNineForPalindrome(n);

        // to find nearest
        if (diff2 <= diff1 && diff2 <= diff3) {
            return b;
        } else if (diff1 <= diff3 && diff1 <= diff2) {
            return a;
        } else {
            return c;
        }
    }
    private String defaultPalindrome(String n) {
        String a = mirroring(n);
        diff1 = Math.abs(Long.parseLong(n) - Long.parseLong(a));
        if (diff1 == 0) {
            diff1 = Long.MAX_VALUE;
        }
        return a;
    }
    private String mirroring(String n) {
        int len = n.length();
        String half = n.substring(0, (len/2));
        String reverse = new StringBuilder(half).reverse().toString();
        if (len % 2 == 1) {
            return half + n.charAt(len / 2) + reverse;
        }
        return half + reverse;
    }
    private String handleZeroForPalindrome(String n) {
        StringBuilder sb = new StringBuilder(n);
        int i = (sb.length() - 1) / 2;
        /**
         * from central to leftward
         * replace every 0 to 9 until not 0
         *
         * 100 -> 99; 200-> 199
         */
        while (i >= 0 && sb.charAt(i) == '0') {
            sb.replace(i, i+1, "9");
            i--;
        }
        // if index reach left most and digit is '1', then need to delete it
        // also replace middle digit to 9
        if (i == 0 && sb.charAt(i) == '1') {
            sb.delete(0, 1);
            int mid = (sb.length() - 1) / 2;
            sb.replace(mid, mid+1, "9");
        } else {
            // else decrement 1 for leftmost digit
            sb.replace(i, i+1, Integer.toString(sb.charAt(i) - 1));
        }
        String b = mirroring(sb.toString());
        diff2 = Math.abs(Long.parseLong(n) - Long.parseLong(b));
        return b;
    }
    private String handleNineForPalindrome(String n) {
        StringBuilder sb = new StringBuilder(n);
        int i = (sb.length() - 1) / 2;
        /**
         * handle
         * 99 -> 100, 199 -> 200
         */
        while (i >= 0 && sb.charAt(i) == '9') {
            sb.replace(i, i+1, "0");
            i--;
        }
        if (i < 0) {
            // when reach left most, if i < 0, then add new digit '1'
            sb.insert(0, "1");
        } else {
            sb.replace(i, i+1, Integer.toString(sb.charAt(i) + 1));
        }
        String c = mirroring(sb.toString());
        diff3 = Math.abs(Long.parseLong(n) - Long.parseLong(c));
        return c;
    }


    /**
     * case 1 : n is palindrome && len(n) is even
     *      decrease two chars in the middle
     * case 2 : n is palindrome && len(n) is odd
     *      decrease one char in middle
     * case 3 : n is not && n is not 1[0..0] pattern
     *      reverse copy (0,l/2) to (l/2 + 1,l)
     * case 4 : n is not && n is 1[0..]
     *      return [9...9]
     */
    public static void main(String[] args) {
        FindClosestPalindrome_564 f = new FindClosestPalindrome_564();
        String s1 = "807045053224792883";
        f.nearestPalindromicII(s1);
        String[] arr = {"123","1221","100","101","99","11","11011"};
        String[] expecet = {"121","1111","99","99","101","9","11111"};
    }

    /** below method cannot cover all test cases */
    public String nearestPalindromic(String n) {
        if (n == null || n.length() == 0) {
            return "";
        }
        if (n.equals("11")) {
            return "9";
        }
        char[] chars = n.toCharArray();
        boolean isPalindrome = isPalindromic(chars);
        boolean isEven = (chars.length & 1) == 0;
        int len = chars.length;
        boolean isOneZeros = isOneZerosPattern(n);
        boolean isAllNines = isAllNinePattern(n);

        if (isPalindrome) {
            if (isAllNines) {
                return generateOneZerosOne(len);
            }
            if (isEven) {
                updateArray(chars, len/2 - 1, len/2);
            } else {
                updateArray(chars, len/2, len/2);
            }
        } else {
            if (isOneZeros) {
                return generateAllNine(len - 1);
            } else {
                copyHalf(chars);
            }
        }
        return new String(chars);
    }
    private boolean isPalindromic(char[] chars) {
        int start = 0, end = chars.length - 1;
        while (start < end) {
            if (chars[start] != chars[end]) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
    private void updateArray(char[] chars, int left, int right) {
        if (left == right) {
            if (chars[left] == '0') {
                chars[left]++;
            } else {
                chars[left]--;
            }
            return;
        }
        while (left < right) {
            chars[left]--;
            chars[right]--;
            left++;
            right--;
        }
    }
    private void copyHalf(char[] chars) {
        int left = 0, right = chars.length - 1;
        while (left < right) {
            chars[right] = chars[left];
            left++;
            right--;
        }
    }
    private boolean isOneZerosPattern(String n) {
        String pattern = "[1]{1}0+";
        return n.matches(pattern);
    }
    private boolean isAllNinePattern(String n) {
        String pattern = "[9]{1}9+";
        return n.matches(pattern);
    }
    private String generateAllNine(int len) {
        StringBuilder sb = new StringBuilder();
        while (len-- > 0) {
            sb.append(9);
        }
        return sb.toString();
    }
    private String generateOneZerosOne(int len) {
        char[] chars = new char[len + 1];
        Arrays.fill(chars, '0');
        chars[0] = '1';
        chars[len] = '1';
        return new String(chars);
    }
}
