package com.leetcode.backtrack;

import com.sun.tools.corba.se.idl.StringGen;
import sun.plugin2.message.SetJVMIDMessage;

import java.util.*;

/**
 * Created by charles on 1/22/17.
 * Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

 Only one letter can be changed at a time
 Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 For example,

 Given:
 beginWord = "hit"
 endWord = "cog"
 wordList = ["hot","dot","dog","lot","log","cog"]
 Return
 [
 ["hit","hot","dot","dog","cog"],
 ["hit","hot","lot","log","cog"]
 ]
 */
public class WordLadderII_126 {
    /**
     * Thought, use BFS to find shortest path distance and construct whole map
     * then use DFS and previous constructed map to recursively find all answers
     */
    public List<List<String>> findLadders(String beginWord, String endWord, Set<String> dict) {
        List<List<String>> ladders = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>(); // ladder of string from single word
        Map<String, Integer> distanceMap = new HashMap<>(); // for this string key, value is steps of replacement

        dict.add(beginWord);
        dict.add(endWord);

        bfs(map, distanceMap, beginWord, endWord, dict);

        List<String> path = new ArrayList<>();
        dfs(ladders, path, beginWord, endWord, distanceMap, map);

        return ladders;
    }

    void bfs(Map<String, List<String>> map, Map<String, Integer> distanceMap, String begin, String end, Set<String> dict) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(begin);
        distanceMap.put(begin, 0);
        for (String s : dict) {
            map.put(s, new ArrayList<String>());
        }

        while (!queue.isEmpty()) {
            String curr = queue.poll();

            List<String> ladder = generateNextLadder(curr, dict);
            for(String next : ladder) {
                if (!distanceMap.containsKey(next)) {
                    distanceMap.put(next, distanceMap.get(curr) + 1);
                    queue.offer(next);
                }
            }
        }
    }

    void dfs(List<List<String>> ladders, List<String> path, String beginWord, String endWord, Map<String, Integer> distanceMap, Map<String, List<String>> map) {
        path.add(endWord);
        if (endWord.equals(beginWord)) {
            Collections.reverse(path);
            ladders.add(new ArrayList<>(path)); // make a copy of list before add into res
            Collections.reverse(path);
        } else {
            for (String next : map.get(endWord)) {
                if (distanceMap.containsKey(next) &&
                        distanceMap.get(endWord) == distanceMap.get(next) + 1) {
                    dfs(ladders, path, beginWord, next, distanceMap, map);
                }
            }
        }
        path.remove(path.size() - 1);
    }

    private List<String> generateNextLadder(String str, Set<String> dict) {
        List<String> nextLadder = new ArrayList<>();
        char[] letters = str.toCharArray();
        char origin = ' ';
        String nextWord = null;
        for (int i = 0; i < letters.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == letters[i]) {
                    continue;
                }
                origin = letters[i];
                letters[i] = c;
                nextWord = String.valueOf(letters);
                if (dict.contains(nextWord)) {
                    nextLadder.add(nextWord);
                }
                // reset
                letters[i] = origin;
            }
        }
        return nextLadder;
    }

    /**
     * Bi-directional solution
     */
    public List<List<String>> findLaddersII(String start, String end, Set<String> dict) {
        // hash set for both
        Set<String> frontSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();

        frontSet.add(start);
        endSet.add(end);

        // use Map to help construct final result
        Map<String, List<String>> map = new HashMap<>();
        // bfs to build map
        bfsHelper(dict, frontSet, endSet, map, false);

        List<List<String>> res = new ArrayList<>();
        List<String> path = new ArrayList<>(Arrays.asList(start));

        // recursively build final result
        dfsHelper(start, end, map, path, res);
        return res;
    }

    private boolean bfsHelper(Set<String> dict, Set<String> frontSet, Set<String> endSet, Map<String, List<String>> map, boolean flip) {
        if (frontSet.isEmpty()) {
            return false;
        }
        if (frontSet.size() > endSet.size()) {
            return bfsHelper(dict, endSet, frontSet, map, !flip);
        }

        // remove words on current both ends from dict
        dict.removeAll(frontSet);
        dict.removeAll(endSet);

        // as we only need shortest paths
        // we use a boolean value to help early termination
        boolean done = false;

        // set for next ladder
        Set<String> ladder = new HashSet<>();
        char[] letters = null;
        char origin = ' ';
        String nextWord = null;
        for (String str : frontSet) {
            letters = str.toCharArray();
            for (int i = 0; i < letters.length; i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    origin = letters[i];
                    letters[i] = c;
                    nextWord = String.valueOf(letters);

                    // make sure we construct tree int he correct direction
                    String key = flip ? nextWord : str;
                    String val = flip ? str : nextWord;

                    List<String> list = map.containsKey(key) ? map.get(key) : new ArrayList<>();
                    if (endSet.contains(nextWord)) {
                        done = true;
                        list.add(val);
                        map.put(key, list);
                    }
                    if (!done && dict.contains(nextWord)) {
                        ladder.add(nextWord);
                        list.add(val);
                        map.put(key, list);
                    }
                }
            }
        }
        // early terminate if done is true;
        return done || bfsHelper(dict, endSet, frontSet, map, !flip);
    }

    private void dfsHelper(String start, String end, Map<String, List<String>> map, List<String> path, List<List<String>> res) {
        if (start.equals(end)) {
            res.add(new ArrayList<>(path));
            return;
        }
        // need this check in case the diff bettwen start and end happend to be one
        // e.g "a", "b", "c", {a, b, c}
        if (!map.containsKey(start)) {
            return;
        }
        for (String word : map.get(start)) {
            path.add(word);
            dfsHelper(word, end, map, path, res);
            path.remove(path.size() - 1);
        }
    }
}
