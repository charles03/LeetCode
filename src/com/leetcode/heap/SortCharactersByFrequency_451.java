package com.leetcode.heap;

/**
 * Created by charles on 1/11/17.
 *
 * Given a string, sort it in decreasing order based on the frequency of characters.
 Example 1:
 Input:         Output:
 "tree          "eert"

 Explanation:
 'e' appears twice while 'r' and 't' both appear once.
 So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
 */
public class SortCharactersByFrequency_451 {

    public String frequencySort(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() < 3) { // only 2 characters
            return s;
        }
        char[] ch = s.toCharArray();

        int maxCount = 0;
        int[] countMap = new int[256];

        for (char c : ch) {
            countMap[c]++;
            maxCount = Math.max(countMap[c], maxCount);
        }

        /** due to characters may appear same count, thus should use string to store all chars under same count */
        String[] buckets = new String[maxCount + 1];
        int count = 0;
        String charsWithSameCount = null;
        for (int i = 0; i < 256; i++) {
            count = countMap[i];
            if ( count > 0) {
                charsWithSameCount = String.valueOf((char)i);
                // combine all char with same counts as string
                if (buckets[count] == null) {
                    buckets[count] = charsWithSameCount;
                } else {
                    buckets[count] += charsWithSameCount;
                }
            }
        }
        StringBuilder sb = new StringBuilder();

        for (int i = maxCount; i >= 0; i--) {
            if (buckets[i] == null) {
                continue;
            }
            // iterate all unique char under same count
            for (char c : buckets[i].toCharArray()) {
                for (int j = 0; j < i; j++) {
                    // append this char with current count times
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SortCharactersByFrequency_451 c = new SortCharactersByFrequency_451();

        System.out.println(c.frequencySort("tree"));
        System.out.println(c.frequencySort("cccaaAbba"));
    }
}
