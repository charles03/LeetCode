package com.leetcode.dynamicprogram;

import java.util.*;

/**
 * Created by charles on 1/25/17.
 * Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.
 A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.

 Example:
 Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
 Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]

 Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
 "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
 "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
 Note:
 The number of elements of the given array will not exceed 10,000
 The length sum of elements in the given array will not exceed 600,000.
 All the input string will only include lower case letters.
 The returned elements order does not matter.
 */
public class ConcatenatedWords_472 {
    /**
     * this is extension of {@link WordBreak_139}
     */
    public List<String> findAllConcatenatedWordsInDict(String[] words) {
        List<String> res = new ArrayList<>();
        Set<String> dict = new HashSet<>();

        // first to Sort original string array from small to long
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        // lambda
//        Arrays.sort(words, ((o1,o2) -> o1.length() - o2.length());

        for (int i = 0; i < words.length; i++) {
            if (canBeSegmented(words[i], dict)) {
                res.add(words[i]);
            }
            dict.add(words[i]);
        }
        return res;
    }

    public boolean canBeSegmented(String word, Set<String> dict) {
        if (dict.isEmpty()) {
            return false;
        }

        boolean[] dp = new boolean[word.length() + 1];
        dp[0] = true;
        int maxLen = getMaxLength(dict);
        int count = 0;
        for (int i = 1; i <= word.length(); i++) {
            for (int j = 1; j <= maxLen && j <= i; j++) {
                if (!dp[i-j]) {
                    continue;
                }
                // to ensure that word is completely made up of other words
                if (dict.contains(word.substring(i-j, i))) {
                    dp[i] = true;
                    count++;
                    break;
                }
            }
        }
        return count > 1 && dp[word.length()];
    }

    private int getMaxLength(Set<String> dict) {
        int max = 0;
        for (String s : dict) {
            max = Math.max(max, s.length());
        }
        return max;
    }

    /**
     * Another Solution is Trie,
     * If we consider word length to be constant (may be at max 100) then to construct Trie it takes O(N)
     * and the searchWord method will have average O(N) if we ignore constants like looping 26 times at each node.
     */
    private static class TrieNode {
        TrieNode[] nodes = new TrieNode[26];
        String word;
        boolean combo = false;
        boolean end = false;
        boolean added = false;
    }

    public List<String> findAllConcatenateWordInDictByTrie(String[] words) {
        TrieNode root = new TrieNode();
        // construct trie
        for (String word : words) {
            if (word.length() == 0) {
                continue;
            }
            constructTrie(root, word);
        }
        List<String> res = new ArrayList<>();
        TrieNode n1 = root;
        TrieNode n2 = root;
        searchTrie(res, n1, n2, root, 0);

        return res;
    }

    public void constructTrie(TrieNode root, String word) {
        TrieNode node = root;

        for (char c : word.toCharArray()) {
            if (node.nodes[c - 'a'] == null) {
                node.nodes[c - 'a'] = new TrieNode();
            }
            node = node.nodes[c - 'a'];
        }
        // when drill down finished
        node.end = true;
        node.word = word;
    }

    public void searchTrie(List<String> res, TrieNode n1, TrieNode n2, TrieNode root, int wordCount) {
        // if node_2 has participated in word combination then need to terminate lookup
        if (n2.combo) {
            return;
        }
        if (n2.end) {
            // if current word is a combination, at least two words and not been added then add it to result
            if (n1.end && !n1.added && wordCount > 1) {
                res.add(n1.word);
                n1.combo = true;
                n1.added = true;
            }
            // need to keep searching with rest of node_1 as it may be combination of more than two words
            searchTrie(res, n1, root, root, wordCount + 1);
        }

        // this will do dfs for each node
        for (int i = 0; i < 26; i++) {
            if (n1.nodes[i] != null && n2.nodes[i] != null) {
                searchTrie(res, n1.nodes[i], n2.nodes[i], root, wordCount);
            }
        }
    }

    /**
     * another solution in trie, above solution has issue, cannot pass test case
     */
    public List<String> findAllConcatenateWordInDictByTrieII(String[] words) {
        Trie root = new Trie();
        // construct trie
        for (String word : words) {
            if (word.length() == 0) {
                continue;
            }
            buildTrie(root, word);
        }
        List<String> res = new ArrayList<>();

        for (String word : words) {
            if (dfs(word, root, 0, root)) {
                res.add(word); // check each word using trie
            }
        }
        return res;
    }

    private static class Trie {
        Trie[] nodes = new Trie[26];
        String word = null;
    }

    private void buildTrie(Trie root, String word) {
        Trie node = root;
        for (char c : word.toCharArray()) {
            if (node.nodes[c - 'a'] == null) {
                node.nodes[c - 'a'] = new Trie();
            }
            node = node.nodes[c - 'a'];
        }
        node.word = word;
    }

    private boolean dfs(String s, Trie t,  int p, Trie root) {
        if (p == s.length()) {
            // return true, if we reach a word at end && original word is concatenated word
            return t.word != null && !t.word.equals(s);
        }
        if (t.nodes[s.charAt(p) - 'a'] == null) {
            return false;
        }
        if (t.nodes[s.charAt(p) - 'a'].word != null && dfs(s, root, p + 1, root)) {
            // reach a word, try to start from root again
            return true;
        }
        // keep going
        return dfs(s, t.nodes[s.charAt(p) - 'a'], p + 1, root);
    }

}
