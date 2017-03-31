package com.leetcode.backtrack;

import java.util.*;

/**
 * Created by charles on 1/22/17.
 * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

 Only one letter can be changed at a time.
 Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 For example,

 Given:
 beginWord = "hit"
 endWord = "cog"
 wordList = ["hot","dot","dog","lot","log","cog"]
 As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 return its length 5.

 Note:
 Return 0 if there is no such transformation sequence.
 All words have the same length.
 All words contain only lowercase alphabetic characters.
 You may assume no duplicates in the word list.
 You may assume beginWord and endWord are non-empty and are not the the same.
 */
public class WordLadder_127 {

    /** because Leetcode change api, so convert list into set */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null) {
            System.out.println("return ");
            return 0;
        }
        if (beginWord.equals(endWord)) {
            return 1;
        }
        // add word in dict list into set
        Set<String> dict = new HashSet<>();
        for (String word : wordList) {
            dict.add(word);
        }
//        return ladderLength(beginWord, endWord, dict);
        return ladderLengthByTwoEnd(beginWord, endWord, dict);
    }

    public int ladderLength(String beginWord, String endWord, Set<String> dict) {
        dict.add(beginWord);
        dict.add(endWord);

        Set<String> visitSet = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.offer(beginWord);
        visitSet.add(beginWord);
        int distance = 1;
        // below two var used inside loop
        int size = 0;
        String word = null;
        List<String> nextLadder = null;

        while (!queue.isEmpty()) {
            distance++; // for next layer
            size = queue.size();
            for (int i = 0; i < size; i++) {
                word = queue.poll();
                nextLadder = getNextWordLadder(word, dict); // from current word, get word ladder
                for (String nextWord : nextLadder) {
                    if (visitSet.contains(nextWord)) { // already visited
                        continue;
                    }
                    if (nextWord.equals(endWord)) { // find endword
                        return distance;
                    }
                    visitSet.add(nextWord);
                    queue.add(nextWord);
                }
            }
        }
        return 0;
    }

    private List<String> getNextWordLadder(String word, Set<String> dict) {
        List<String> ladder = new ArrayList<>();
        char[] letters = null;
        String nextWord = null;
        for (char c = 'a'; c <= 'z'; c++) {
            letters = word.toCharArray();
            for (int i = 0; i < letters.length; i++) {
                if (c == letters[i]) {
                    continue;
                }
                /** should not use below way to find next word */
                /** should alway work in new char array of original word */
                /*letters[i] = c;
                nextWord = new String(letters);*/
                nextWord = replace(word, i, c);
                if (dict.contains(nextWord)) {
                    ladder.add(nextWord);
                }
            }
        }
        return ladder;
    }

    private String replace(String word, int index, char c) {
        char[] chars = word.toCharArray();
        chars[index] = c;
        return new String(chars);
    }

    /**
     * Optimization is with bidirectional BFS, when we confirm both start and end
     * Time Complexity will be sqrt of original Time Complexity
     */
    public int ladderLengthByTwoEnd(String beginWord, String endWord, Set<String> dict) {
        // need two sets for both start direction and end direction
        Set<String> frontSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> visitSet = new HashSet<>();
        Set<String> tmp = null; // tmp Set for switch
        char[] letters = null;
        char origin = ' ';
        String nextWord = null;
        int distance = 1;

        frontSet.add(beginWord);
        endSet.add(endWord);

        while (!frontSet.isEmpty() && !endSet.isEmpty()) {
            if (frontSet.size() > endSet.size()) {
                // alway use small size as frontSet
                // to switch front set and end set
                tmp = frontSet;
                frontSet = endSet;
                endSet = tmp;
            }
            Set<String> singleLadder = new HashSet<>();
            for (String word : frontSet) {
                letters = word.toCharArray();
                for (int i = 0; i < letters.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        origin = letters[i];
                        letters[i] = c; // update new value
                        nextWord = String.valueOf(letters);
                        if (endSet.contains(nextWord)) {
                            return distance + 1;
                        }
                        if (!visitSet.contains(nextWord) && dict.contains(nextWord)) {
                            singleLadder.add(nextWord);
                            visitSet.add(nextWord);
                        }
                        letters[i] = origin; // reset back to original word
                    }// end 26 letters
                }// end word length
            }// end whole set
            frontSet = singleLadder; // move to next ladder;
            distance++; // add layer
        }
        return 0;
    }

    public static void main(String[] args) {
        WordLadder_127 w = new WordLadder_127();
        String[] words = {"hot","dot","dog","lot","log","cog"};
        List<String> list = new ArrayList<String>(Arrays.asList(words));

        int res = w.ladderLength("hit", "cog", list);

        System.out.println(res);
    }

    private void output(List<String> list) {
        list.stream().forEach(t -> System.out.print(t + ","));
        System.out.println();
    }
}
