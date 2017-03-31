package com.leetcode.string;

/**
 * Created by charles on 3/25/17.
 * Given a word, you need to judge whether the usage of capitals in it is right or not.

 We define the usage of capitals in a word to be right when one of the following cases holds:

 All letters in this word are capitals, like "USA".
 All letters in this word are not capitals, like "leetcode".
 Only the first letter in this word is capital if it has more than one letter, like "Google".
 Otherwise, we define that this word doesn't use capitals in a right way.
 Example 1:
 Input: "USA"
 Output: True
 Example 2:
 Input: "FlaG"
 Output: False
 */
public class DetectCapital_520 {
    /**
     * Naive solution, check for cases
     */
    public boolean detectCapitalUse(String word) {
        char[] chars = word.toCharArray();
        int len = chars.length;
        if (len == 1) {
            return true;
        }
        if (Character.isUpperCase(chars[0])) {
            if (Character.isUpperCase(chars[1])) {
                return isOnlyUpper(chars, 2);
            }
            return isOnlyLower(chars, 1);
        } else {
            return isOnlyLower(chars, 0);
        }
    }

    private boolean isOnlyUpper(char[] chars, int start) {
        boolean isUpper = true;
        for (int i = start; i < chars.length; i++) {
            isUpper &= Character.isUpperCase(chars[i]);
        }
        return isUpper;
    }
    private boolean isOnlyLower(char[] chars, int start) {
        boolean isLower = true;
        for (int i = start; i < chars.length; i++) {
            isLower &= Character.isLowerCase(chars[i]);
        }
        return isLower;
    }

    /** better solution, Regex */
    public boolean detectCapitalUseII(String word) {
        String regex = "[A-Z]*|[A-Z]?[a-z]*";
        return word.matches(regex);
    }
}
