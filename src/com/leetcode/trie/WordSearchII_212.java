package com.leetcode.trie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 5/3/17.
 * Given a 2D board and a list of words from the dictionary, find all words in the board.

 Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 For example,
 Given words = ["oath","pea","eat","rain"] and board =

 [
 ['o','a','a','n'],
 ['e','t','a','e'],
 ['i','h','k','r'],
 ['i','f','l','v']
 ]
 Return ["eat","oath"].
 Note:
 You may assume that all inputs are consist of lowercase letters a-z.

 click to show hint.

 You would need to optimize your backtracking to pass the larger test. Could you stop backtracking earlier?

 If the current candidate does not exist in all words' prefix, you could stop backtracking immediately. What kind of data structure could answer such query efficiently? Does a hash table work? Why or why not? How about a Trie? If you would like to learn how to implement a basic trie, please work on this problem: Implement Trie (Prefix Tree) first
 */
public class WordSearchII_212 {
    private class SimpleTrieNode {
        SimpleTrieNode[] link = new SimpleTrieNode[26];
        String word;
    }

    public List<String> findWord(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        SimpleTrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, res);
            }
        }
        return res;
    }

    private SimpleTrieNode buildTrie(String[] words) {
        SimpleTrieNode root = new SimpleTrieNode();
        SimpleTrieNode node;
        int idx;
        for (String word : words) {
            node = root;
            for (char c : word.toCharArray()) {
                idx = c - 'a';
                if (node.link[idx] == null) {
                    node.link[idx] = new SimpleTrieNode();
                }
                node = node.link[idx];
            }
            node.word = word;
        }
        return root;
    }
    private void dfs(char[][] board, int i, int j, SimpleTrieNode root, List<String> res) {
        char c = board[i][j];
        if (c == '#' || root.link[c - 'a'] == null) {
            return; // early termination
        }
        root = root.link[c - 'a'];
        if (root.word != null) {
            // found one;
            res.add(root.word);
            // de-duplicate, set to null
            root.word = null;
        }
        // set board[i,j] to other character so as to play role as visiting hash table
        board[i][j] = '#';
        if (i > 0) {
            dfs(board, i-1, j, root, res);
        }
        if (j > 0) {
            dfs(board, i, j-1, root, res);
        }
        if (i < board.length - 1) {
            dfs(board, i+1, j, root, res);
        }
        if (j < board[0].length - 1) {
            dfs(board, i, j+1, root, res);
        }
        // after dfs backtracking,
        // set change back to original character in board
        board[i][j] = c;
    }

}

