package com.leetcode.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by charles on 5/18/17.
 * Given a list of strings, you need to find the longest uncommon subsequence among them. The longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any subsequence of the other strings.

 A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a subsequence of any string.

 The input will be a list of strings, and the output needs to be the length of the longest uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.

 Example 1:
 Input: "aba", "cdc", "eae"
 Output: 3
 */
public class LongestUncommonSubsequenceII_522 {
    /**
     * {@link LongestUncommonSubsequence_521}
     * last approach, we compare all given strings and compare them for subsequence
     * we can save computation if we sort given set of strings based on length
     * sort string in reverse order, if there is no duplicates in array,
     * then longest string is answer
     * but if there are dups and if longest string is not answer,
     * then we need to check other, continue same process by choosing second largest strign as first string
     * and repeat process and so on.
     */
    public int findLUSlength(String[] strs) {
        // longest length at front
        Arrays.sort(strs, (a,b) -> b.length() - a.length());
        Set<String> duplicates = getDuplicates(strs);

        for (int i = 0; i < strs.length; i++) {
            if (duplicates.contains(strs[i])) {
                continue;
            }
            if (i == 0) { // longest string without dups and also its length is longest length of uncommon subsequence
                return strs[0].length();
            }
            // check if current str[i] is subsequence of all strings which length is longer than this.
            for (int k = 0; k < i; k++) {
                if (isSubsequence(strs[k], strs[i])) {
                    break; // only break inner for loop
                }
                if (k == i-1) { // current string is answer
                    return strs[i].length();
                }
            }
        }
        return -1;
    }
    private Set<String> getDuplicates(String[] strs) {
        Set<String> set = new HashSet<>();
        Set<String> dup = new HashSet<>();
        for (String s : strs) {
            if (!set.add(s)) {
                dup.add(s);
            }
        }
        return dup;
    }
    private boolean isSubsequence(String a, String b) {
        int i = 0, j = 0;
        while (i < a.length() && j < b.length()) {
            if (a.charAt(i) == b.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == b.length();
    }
}
