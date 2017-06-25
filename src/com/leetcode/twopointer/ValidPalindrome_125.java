package com.leetcode.twopointer;

/**
 * Created by charles on 4/12/17.
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

 For example,
 "A man, a plan, a canal: Panama" is a palindrome.
 "race a car" is not a palindrome.

 Note:
 Have you consider that the string might be empty? This is a good question to ask during an interview.

 For the purpose of this problem, we define empty string as valid palindrome.
 */
public class ValidPalindrome_125 {
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] chars = s.toCharArray();
        int start = 0, end = chars.length - 1;
        int diff = 0;
        while (start < end) {
            /*if (!Character.isAlphabetic(chars[start]) && !Character.isDigit(chars[start])) {
                start++;
                continue;
            }*/
            /** simplified */
            if (!Character.isLetterOrDigit(chars[start])) {
                start++;
                continue;
            }
            if (!Character.isLetterOrDigit(chars[end])) {
                end--;
                continue;
            }
            if (Character.toLowerCase(chars[start]) != Character.toLowerCase(chars[end])) {
                return false;
            }
            /** below implement fail in test case "0P"
            /*diff = Math.abs(chars[start] - chars[end]);
            if (diff != 0 && diff != 32) {
                return false;
            }*/
            start++;
            end--;
        }
        return true;
    }
    public static void main(String[] args) {
        ValidPalindrome_125 v = new ValidPalindrome_125();
        String s3 = "0P";
        System.out.println(v.isPalindrome(s3));

        String s1 = "A man, a plan, a canal: Panama";
//        System.out.println('A' - 'a');
        System.out.println(v.isPalindrome(s1));
        String s2 = "race a car";
        System.out.println(v.isPalindrome(s2));

    }
}
