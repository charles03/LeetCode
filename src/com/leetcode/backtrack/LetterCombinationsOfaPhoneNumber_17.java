package com.leetcode.backtrack;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 1/19/17.
 * Given a digit string, return all possible letter combinations that the number could represent.
 A mapping of digit to letters (just like on the telephone buttons) is given below.
 Input:Digit string "23"
 Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 */
public class LetterCombinationsOfaPhoneNumber_17 {
    private static final String[] KEYS = {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        List<String> res = new LinkedList<>();
        char[] index = digits.toCharArray();
        combineHelper("", index, 0, res);
        return res;
    }

    private void combineHelper(String str, char[] index, int pos, List<String> res) {
        if (pos >= index.length) {
            res.add(str);
            return;
        }
        char[] letters = KEYS[index[pos] - '0'].toCharArray();

        for (int i = 0; i < letters.length; i++) {
            combineHelper(str + letters[i], index, pos + 1, res);
        }
    }

    public static void main(String[] args) {
        LetterCombinationsOfaPhoneNumber_17 l = new LetterCombinationsOfaPhoneNumber_17();
        output(l.letterCombinations("23"));
    }

    private static void output(List<String> list) {
        list.stream().forEach(t -> System.out.print(t + ','));
        System.out.println();
    }
}
