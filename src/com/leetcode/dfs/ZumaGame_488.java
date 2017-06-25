package com.leetcode.dfs;

import com.leetcode.array.ArrayNesting_565;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 6/15/17.
 * Think about Zuma Game. You have a row of balls on the table, colored red(R), yellow(Y), blue(B), green(G), and white(W). You also have several balls in your hand.

 Each time, you may choose a ball in your hand, and insert it into the row (including the leftmost place and rightmost place). Then, if there is a group of 3 or more balls in the same color touching, remove these balls. Keep doing this until no more balls can be removed.

 Find the minimal balls you have to insert to remove all the balls on the table. If you cannot remove all the balls, output -1.

 Examples:

 Input: "WRRBBW", "RB"
 Output: -1
 Explanation: WRRBBW -> WRR[R]BBW -> WBBW -> WBB[B]W -> WW

 Input: "WWRRBBWW", "WRBRW"
 Output: 2
 Explanation: WWRRBBWW -> WWRR[R]BBWW -> WWBBWW -> WWBB[B]WW -> WWWW -> empty

 Input:"G", "GGGGG"
 Output: 2
 Explanation: G -> G[G] -> GG[G] -> empty

 Input: "RBYYBBRRB", "YRBGB"
 Output: 3
 Explanation: RBYYBBRRB -> RBYY[Y]BBRRB -> RBBBRRB -> RRRB -> B -> B[B] -> BB[B] -> empty

 Note:
 You may assume that the initial row of balls on the table won’t have any 3 or more consecutive balls with the same color.
 The number of balls on the table won't exceed 20, and the string represents these balls is called "board" in the input.
 The number of balls in your hand won't exceed 5, and the string represents these balls is called "hand" in the input.
 Both input strings will be non-empty and only contain characters 'R','Y','B','G','W'.
 */
public class ZumaGame_488 {
    public int findMinStep(String board, String hand) {
        /** key is each char in hand string, value is total count of appearance */
        Map<Character, Integer> map = new HashMap<>();
        for (char c : hand.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return dfs(board, map);
    }
    private int dfs(String balls, Map<Character, Integer> map) {
        String base = truncate(balls);
        if (base.length() <= 1) {
            return edgeCase(base, map);
        }
        int minStep = Integer.MAX_VALUE;
        int left = 0;
        for (int right = 1; right < base.length(); right++) {
            char start = base.charAt(left);
            while (right < base.length() && start == base.charAt(right)) {
                right++;
            }
            int needed = 3 - (right-left);
            if (map.containsKey(start) && map.get(start) >= needed) {
                map.put(start, map.get(start) - needed);
                /**
                 * noted, do not assign truncated str back to orignal string variable
                 * otherwise cannot dfs correctly.
                 *
                 * error statement
                 * base = base.substring() + base.substring
                 * dfs(base)
                 * then after return from dfs, base is changed, which should not be modified
                 */
                String removed = base.substring(0, left) + base.substring(right);
                int nextAllStep = dfs(removed, map);
                map.put(start, map.get(start) + needed);
                if (nextAllStep != -1) {
                    minStep = Math.min(minStep, nextAllStep + needed);
                }
            }
            left = right;
        }
        if (minStep == Integer.MAX_VALUE) {
            return -1;
        }
        return minStep;
    }

    private int edgeCase(String str, Map<Character, Integer> map) {
        if (str.length() == 0) {
            return 0;
        }
        char c = str.charAt(0);
        if (str.length() == 1 && map.containsKey(c) && map.get(c) >= 2) {
            return 2;
        }
        return -1; // cannot truncate
    }
    /** recursive truncate str until there is no consecutive balls left
     * after previous truncate, there will be new available consecutive balls */
    private String truncate(String str) {
        int left = 0;
        char start;
        String removeConsecutive = null;
        for (int right = 1; right < str.length(); right++) {
            start = str.charAt(left);
            while (right < str.length() && start == str.charAt(right)) {
                right++;
            }
            if (right - left >= 3) {
                // recursive truncate new string after previous truncate
                removeConsecutive = str.substring(0, left) + str.substring(right);
                return truncate(removeConsecutive);
            }
            left = right;
        }
        return str; // otherwise return original str
    }

    private int limit = 6;
    public int findMinStepII(String s, String h) {
        int[] count = new int[26]; // extand to 32 is for including "#", '#' - 'A'
        for (int i = 0; i < h.length(); i++) {
            count[h.charAt(i) - 'A']++;
        }
        int min = dfsII(s + "#", count); // append "#" to avoid special process while j == s.length
        if (min == limit) {
            return -1;
        }
        return min;
    }
    private int dfsII(String s, int[] count) {
        s = truncate(s);
        if (s.equals("#")) {
            return 0;
        }
        int min = limit;
        int need = 0;
        int l = 0;
        char start;
        for (int r = 0; r < s.length(); r++) {
            start = s.charAt(l);
            if (start == s.charAt(r)) {
                continue;
            }
            need = 3 - (r-l); // count of ball which need for truncating
            int offset = s.charAt(l) - 'A'; // use left cursor instead of right cursor
            if (count[offset] >= need) {
                count[offset] -= need;
                min = Math.min(min, need + dfsII(s.substring(0,l) + s.substring(r), count));
                count[offset] += need;
            }
            l = r;
        }
        return min;
    }

    public static void main(String[] args) {
        ZumaGame_488 z = new ZumaGame_488();
        String s1 = "WWRRBBWW";
        String h1 = "WRBRW";
        System.out.println(z.findMinStep(s1, h1));

//        System.out.println(z.findMinStepII(s1, h1));
    }

}
