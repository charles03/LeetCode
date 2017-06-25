package com.leetcode.trie;

import sun.text.normalizer.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by charles on 4/29/17.
 * Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.

 Example 1:
 Given words = ["bat", "tab", "cat"]
 Return [[0, 1], [1, 0]]
 The palindromes are ["battab", "tabbat"]
 Example 2:
 Given words = ["abcd", "dcba", "lls", "s", "sssll"]
 Return [[0, 1], [1, 0], [3, 2], [2, 4]]
 The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]

 */
public class PalindromePairs_336 {
    /**
     * Naive solution, attempt all combinations
     * then add indexes into result
     */
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        int size = words.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j && isPalindrome(words[i] + words[j])) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(j);
                    res.add(list);
                }
            }
        }
        return res;
    }
    private boolean isPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
        }
        return true;
    }
    private boolean isPalindrome(String str, int i, int j) {
        while (i < j) {
            if (str.charAt(i++) != str.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Trie solution:
     * option 1, for each word, add its reverse word into Trie
     *      then in search, pair word should be part in trie,
     *      the rest should be null or is palindrome
     * option 2, for each word, while in adding,
     *      record index into trie when str[0,i] is palindrome
     *      then in search, no need to check palindrome again.
     */
    private class SimpleTrieNode {
        SimpleTrieNode[] link;
        int index;
        List<Integer> list; // for each word, add i when str[0,i] is palindrome

        SimpleTrieNode() {
            // init
            link = new SimpleTrieNode[26];
            index = -1;
            list = new ArrayList<>();
        }
    }

    public List<List<Integer>> palindromePairsII(String[] words) {
        List<List<Integer>> res = new ArrayList<>();

        SimpleTrieNode root = new SimpleTrieNode();
        int size = words.length;
        for (int i = 0; i < size; i++) {
            addWord(root, words[i], i);
        }
        for (int i = 0; i < size; i++) {
            search(words[i], i, root,  res);
        }
        return res;
    }
    /** for each word, add reverse word into Trie
     * meanwhile, record index of array of words when word[index]
     * has leading palindrome part.
     * */
    private void addWord(SimpleTrieNode root, String word, int index) {
        int j;
        for (int i = word.length() - 1; i >= 0; i--) {
            j = word.charAt(i) - 'a';
            if (root.link[j] == null) {
                root.link[j] = new SimpleTrieNode();
            }
            if (isPalindrome(word, 0, i)) {
                root.list.add(index);
            }
            root = root.link[j];
        }
        root.list.add(index);
        root.index = index;
    }
    private void search(String word, int i, SimpleTrieNode root, List<List<Integer>> res) {
        for (int j = 0; j < word.length(); j++) {
            if (root.index >= 0 && root.index != i
                    && isPalindrome(word, j, word.length() - 1)) {
                /**
                 * when len(searched)is longer than len(matching reverse)
                 * need to validate searched[j, len-1] is palindrome
                 * because searched is at left
                 * example,
                 * searched sslll, orig: s, in trie, s
                 * sslll + s -> validate (slll) is not palindrome
                 * so ssllls is not palindrome
                 *
                 * another case
                 * searched sslll, orig: lss, in trie: ssl
                 * sslll --> search in trie, -> then to validate (ll) is palindrome
                 * ssl
                 * thus ssllllss is palindrome
                 */

                System.out.println("1---"+ i + ", " + j + ", " + root.index);
                res.add(Arrays.asList(i, root.index));
            }
            root = root.link[word.charAt(j) - 'a'];
            if (root == null) {
                return;
            }
        }
        // trie.list is to record point where having palindrome part
        // for the case when len(searched) is smaller than matching reverse
        for (int j : root.list) {
            if (i == j) {
                continue;
            }
            System.out.println("2---"+ i + ", " + j);
            res.add(Arrays.asList(i, j));
        }
    }

    public static void main(String[] args) {
        PalindromePairs_336 p = new PalindromePairs_336();
        String[] words = {"abcd","dcba","lls","s","sssll"};
        p.palindromePairsII(words);
    }
}

