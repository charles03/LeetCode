package com.leetcode.string;

/**
 * Created by charles on 3/2/17.
 * Given a string that consists of only uppercase English letters, you can replace any letter in the string with another letter at most k times. Find the length of a longest substring containing all repeating letters you can get after performing the above operations.

 Note:
 Both the string's length and k will not exceed 104.

 Example 1:

 Input:
 s = "ABAB", k = 2

 Output:
 4

 Explanation:
 Replace the two 'A's with two 'B's or vice versa.
 Example 2:

 Input:
 s = "AABABBA", k = 1

 Output:
 4

 Explanation:
 Replace the one 'A' in the middle with 'B' and form "AABBBBA".
 The substring "BBBB" has the longest repeating letters, which is 4.

 */
public class LongestRepeatingCharacterReplacement_424 {
    /**
     * The key point is we are focusing on "longest".
     So assume we initially found a valid substring, what do we do next?

     Still valid even appended by one more char——then we are happy, just expand the window
     It became invalid. But at this time, do we have to shrink the window?
     ——No, we shift the whole window rightwards by one unit. So that the length is unchanged.
     The reason for that is , any index smaller than original "start", will never have the chance to lead a longer valid substring than current length of our window.
     Do we need to update max in time?
     ——No. Once again, we focus on "longest". The length of valid substring is determined by what?
     Max Occurrence + k
     We only need to update max occurrence when it becomes larger, because only that can we generate a longer valid substring.
     If we can't surpass the historic max occurrence, then we can not generate a longer valid substring from current "start", I mean the new windows's left bound. To some extend, this becomes a game to find max occurrence.
     Or , a better understanding is:
     "A game to eliminate inferior 'left bounds'.
     */
    public int characterReplacement(String s, int k) {
        int[] occurs = new int[26]; // only uppercase english letter
        int maxOccur = 0;
        int start = 0;
        int len = s.length();
        for (int end = 0; end < len; end++) {
            // to get current max occurrence of letter
            maxOccur = Math.max(maxOccur, ++occurs[s.charAt(end) - 'A']);

            if ((end - start + 1) - maxOccur > k) {
                // end-start+1 denotes size of current window
                // subtraction means repeating times we edit all chars that are not char for maxOccur to max length
                // if exceed k times, need to shift window right, which means to remove left char
                occurs[s.charAt(start) - 'A']--;
                start++;
            }
        }
        return len - start;
    }
}
