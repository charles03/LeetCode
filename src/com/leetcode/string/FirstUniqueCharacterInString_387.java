package com.leetcode.string;

/**
 * Created by charles on 3/15/17.
 * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.

 Examples:

 s = "leetcode"
 return 0.

 s = "loveleetcode",
 return 2.
 Note: You may assume the string contain only lowercase letters.
 */
public class FirstUniqueCharacterInString_387 {
    /** use two scans */
    public int firstUniqChar(String s) {
        if (s == null || s.isEmpty()) {
            return -1;
        }
        int[] letters = new int[26];
        for (int i = 0; i < s.length(); i++) {
            letters[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (letters[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
    /** use two pointers  slow and fast */
    /**
     * The idea is to use a slow pointer to point to the current unique character
     * and a fast pointer to scan the string. The fast pointer not only just add the count of the character.
     * Meanwhile, when fast pointer finds the identical character of the character at the current slow pointer,
     * we move the slow pointer to the next unique character or not visited character.
     */
    public int firstUniqCharII(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        int len = s.length();
        if (len == 1) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int fast = 0, slow = 0;
        int[] count = new int[26];

        while (fast < len) {
            count[chars[fast] - 'a']++;
            // if fast pointer is not unique char anymore, move to next unique one
            while (slow < len && count[chars[slow] - 'a'] > 1) {
                slow++;
            }
            if (slow >= len) { // no unique exist
                return -1;
            }
            if (count[chars[slow] - 'a'] == 0) { // not visit by fast
                count[chars[slow] - 'a']++;
                fast = slow; // update fast for new index
            }
            fast++;
        }
        return slow;
    }

    public static void main(String[] args) {
        FirstUniqueCharacterInString_387 f = new FirstUniqueCharacterInString_387();
        String s1 = "abbvadc";
        String s2 = "leetcode";
        System.out.println(f.firstUniqChar(s1));
        System.out.println(f.firstUniqCharII(s1));

        System.out.println(f.firstUniqChar(s2));
        System.out.println(f.firstUniqCharII(s2));
    }
}
