package com.leetcode.sort;

import com.princeton.stdlib.In;

import java.util.*;

/**
 * Created by charles on 5/10/17.
 * Given a string, sort it in decreasing order based on the frequency of characters.

 Example 1:

 Input:
 "tree"

 Output:
 "eert"

 Explanation:
 'e' appears twice while 'r' and 't' both appear once.
 So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
 Example 2:

 Input:
 "cccaaa"

 Output:
 "cccaaa"

 Explanation:
 Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
 Note that "cacaca" is incorrect, as the same characters must be together.
 Example 3:

 Input:
 "Aabb"

 Output:
 "bbAa"

 Explanation:
 "bbaA" is also a valid answer, but "Aabb" is incorrect.
 Note that 'A' and 'a' are treated as two different characters.
 */
public class SortCharactersByFrequency_451 {
    /**
     * two solutions,
     * one: use bucket sort
     * two: use priority queue
     */
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        // create bucket, where each elem is list
        List<Character>[] bucket = new ArrayList[s.length() + 1];
        int frequency = 0;
        for (char key : map.keySet()) {
            frequency = map.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = bucket.length - 1; i >= 0; i--) {
            if (bucket[i] == null) {
                continue;
            }
            for (char c : bucket[i]) {
                for (int k = 0; k < i; k++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public String frequencySortII(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        PriorityQueue<Map.Entry<Character, Integer>> heap = new PriorityQueue<>(
                (a,b) -> b.getValue() - a.getValue()
        );
        heap.addAll(map.entrySet());
        StringBuilder sb = new StringBuilder();
        Map.Entry entry = null;
        while (!heap.isEmpty()) {
            entry = heap.poll();
            for (int i = 0; i < (int)entry.getValue(); i++) {
                sb.append(entry.getKey());
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SortCharactersByFrequency_451 s = new SortCharactersByFrequency_451();
        String s1 = "aaBbAA";
        System.out.println(s.frequencySort(s1));
    }
}
