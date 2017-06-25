package com.leetcode.string;

import java.util.*;

/**
 * Created by charles on 4/9/17.
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.

 Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.

 The order of output does not matter.

 Example 1:

 Input:
 s: "cbaebabacd" p: "abc"

 Output:
 [0, 6]

 Explanation:
 The substring with start index = 0 is "cba", which is an anagram of "abc".
 The substring with start index = 6 is "bac", which is an anagram of "abc".
 Example 2:

 Input:
 s: "abab" p: "ab"

 Output:
 [0, 1, 2]

 Explanation:
 The substring with start index = 0 is "ab", which is an anagram of "ab".
 The substring with start index = 1 is "ba", which is an anagram of "ab".
 The substring with start index = 2 is "ab", which is an anagram of "ab".
 */
public class FindAllAnagramsInString_438 {
    /** naive solution */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();
        int len = p.length();
        // key char, value count of char appearance in String p
        Map<Character, Integer> baseMap = new HashMap<>();

        for (int i = 0; i < pArr.length; i++) {
            if (baseMap.containsKey(pArr[i])) {
                baseMap.put(pArr[i], baseMap.get(pArr[i]) + 1);
            } else {
                baseMap.put(pArr[i], 1);
            }
        }
        Map<Character, Integer> map = null;
        for (int i = 0; i < sArr.length; i++) {
            map = new HashMap<>(baseMap);
            if (isAnagram(sArr, i, i+len, map)) {
                res.add(i);
            }
        }
        return res;
    }
    private boolean isAnagram(char[] chars, int start, int end, Map<Character, Integer> map) {
//        output(map);
        if (end > chars.length) {
            return false;
        }
        char c;
        for (int i = start; i < end; i++) {
            c = chars[i];
            if (!map.containsKey(c)) {
                return false;
            }
            map.put(c, map.get(c) - 1);
            if (map.get(c) < 0) {
                return false;
            }
        }
//        output(map);
        // iterate map to check if all values of keys are zeros
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() != 0) {
                return false;
            }
         }
        return true;
    }

    public static void main(String[] args) {
        FindAllAnagramsInString_438 f = new FindAllAnagramsInString_438();
        String s1 = "cbaebabacd";
        String p1 = "abc";
        output(f.findAnagrams(s1, p1));
        String s2 = "abab";
        String p2 = "ab";
        output(f.findAnagrams(s2, p2));
        output(f.findAnagramIII(s1,p1));
    }
    private static void output(List<Integer> list) {
        list.stream().forEach(t-> System.out.print(t + ","));
        System.out.println();
    }
    private static void output(Map<?, ?> map) {
        map.forEach((k,v) -> System.out.print(v + ","));
        System.out.println();
    }

    /**
     * Thought:
     * if find one anagram of p (a) in String s,
     * if (a[1] is next start of anagram, then need a[0] = a[end+1])
     * else if (a[end+1] is not in the set of chars of p) {
     *     move index = end + 1;
     * } else if (a[2] is next start of anagram, then need a[0,1] == a[end+1, end+2])
     */
    public List<Integer> findAnagramsII(String s, String p) {
        List<Integer> res = new LinkedList<>();
        if (p.length() > s.length()) {
            return res;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int counter = map.size(); // count of unique chars in original string p

        int start = 0, end = 0;
        int head = 0;
        int sLen = s.length();
        int pLen = p.length();
        while (end < sLen) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) {
                    counter--;
                }
            }
            end++;
            while (counter == 0) {
                // already find all unique chars
                char t = s.charAt(start);
                if (map.containsKey(t)) {
                    // start to add redundant chars
                    map.put(t, map.get(t) + 1);
                    if (map.get(t) > 0) {
                        counter++;
                    }
                }
                // to check whether end-start = length of string p
                if (end - start == pLen) {
                    res.add(start);
                }
                start++;
            }
        }
        return res;
    }

    /** simplified version with better performance */
    public List<Integer> findAnagramIII(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int[] map = new int[128];
        for (char c : p.toCharArray()) {
            map[c]++;
        }
        int start = 0, end = 0;
        int counter = p.length();
        int sLen = s.length(), pLen = p.length();

        while (end < sLen) {
            if (map[s.charAt(end)] > 0) {
                counter--;
            }
            map[s.charAt(end)]--;
            end++;
            while (counter == 0) {
                if (end - start == pLen) {
                    res.add(start);
                }
                if (map[s.charAt(start)] >= 0) {
                    counter++;
                }
                map[s.charAt(start)]++;
                start++;
            }
        }
        return res;
    }

}
