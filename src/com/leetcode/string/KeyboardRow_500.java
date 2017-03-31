package com.leetcode.string;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by charles on 3/23/17.
 * Given a List of words, return the words that can be typed using letters of alphabet on only one row's of American keyboard like the image below.
 * Input: ["Hello", "Alaska", "Dad", "Peace"]
 * Output: ["Alaska", "Dad"]
 */
public class KeyboardRow_500 {
    private char[][] rows = {{'q','w','e','r','t','y','u','i','o','p'},
                             {'a','s','d','f','g','h','j','k','l'},
                             {'z','x','c','v','b','n','m'}};
    private Map<Character, Integer> map = generateMap();

    public String[] findWords(String[] words) {
        List<String> res = new ArrayList<>();
        for (String w : words) {
            if (testWord(w.toLowerCase())) {
                res.add(w);
            }
        }
        return res.toArray(new String[0]);
    }

    private Map<Character, Integer> generateMap() {
        Map<Character, Integer> dict = new HashMap<>();
        for (int i = 0; i < rows.length; i++) {
            for (char c : rows[i]) {
                dict.put(c, i);
            }
        }
        return dict;
    }

    private boolean testWord(String word) {
        int i = 1;
        int len = word.length();
        while (i < len) {
            if (map.get(word.charAt(i)) != map.get(word.charAt(i-1))) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static void main(String[] args) {
        KeyboardRow_500 k = new KeyboardRow_500();
        String[] w1 = {"Hello", "Alaska", "Dad", "Peace"};
        String[] res = k.findWords(w1);
        output(res);
        String[] res1 = k.findWordsII(w1);
        output(res1);
    }

    private static void output(String[] res) {
        Arrays.asList(res).stream().forEach(t -> System.out.print(t + ","));
        System.out.println();
    }

    /** Single one line solution using Lambda */
    public String[] findWordsII(String[] words) {
        String regex = "[qwertyuiop]*|[asdfghjkl]*|[zxcvbnm]*";
        return Stream.of(words).filter(s -> s.toLowerCase().matches(regex)).toArray(String[]::new);
        // String[]::new is a syntax sugar for lambda expression size -> new String[size]
    }
}
