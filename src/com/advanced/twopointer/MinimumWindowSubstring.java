package com.advanced.twopointer;

/**
 * Created by charles on 11/11/16.
 * Given a string source and a string target,
 * find the minimum window in source which will contain all the characters in target.
 *
 * If there is no such window in source that covers all characters in target, return the emtpy string "".
 If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in source.
 Should the characters in minimum window has the same order in target? <Not necessry>

 Example: For source = "ADOBECODEBANC", target = "ABC", the minimum window is "BANC"
 */
public class MinimumWindowSubstring {
    /**
     * @param source: A string
     * @param target: A string
     * @return: A string denote the minimum window
     *          Return "" if there is no such a string
     */
    public String minWindow(String source, String target) {
        if (source == null || target == null) return "";
        if (source.length() == 0 || target.length() == 0) return "";

        char[] s = source.toCharArray();
        char[] t = target.toCharArray();

        char ascii = ' ';

        int[] set = new int[256];
        int targetCount = 0;
        // initialize
        for (char c : t) {
            targetCount++;
            set[c - ascii]++;
        }
        int minLen = Integer.MAX_VALUE;
        int sourceCount = 0;
        int startIndex = 0, endIndex = 0;
        int j = 0;
        int len = 0;
        for (int i = 0; i < s.length; i++) {
            if (set[s[i] - ascii] > 0) {
                sourceCount++;
            }
            set[s[i] - ascii]--;
            while (sourceCount >= targetCount) {
                len = i - j + 1;
                if (minLen > len) {
                    minLen = len;
                    startIndex = j;
                    endIndex = startIndex + len;
                }
                set[s[j] - ascii]++;
                if (set[s[j] - ascii] > 0) {
                    sourceCount--;
                }
                j++;
            }
        }
        return source.substring(startIndex, endIndex);
    }

    public static void main(String[] args) {
        MinimumWindowSubstring m = new MinimumWindowSubstring();
        String source = "ADOBECODEBANC";
        String target = "ABC";

        source = "adfanadsnf;andvadsjfafjdlsfnaldjfi*odiasjfasdlnfasldgao;inadfjnals;dfjasdl;jfa;dsjfa;sdnfsd;afhwery894yra7f78asfas8fy43rhaisuey34hiuy^%(9afysdfhaksdhfsdkjfhsdhfakldhfsdkf*h";
        target = "dafdnf**";

        System.out.println('*' - ' ');
        System.out.println(m.minWindow(source, target));
    }
}
