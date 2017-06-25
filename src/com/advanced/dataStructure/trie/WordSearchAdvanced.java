package com.advanced.dataStructure.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by charles on 10/23/16.
 * Given a matrix of lower alphabets and a dictionary.
 * Find all words in the dictionary that can be found in the matrix.
 * A word can start from any position in the matrix and go left/right/up/down to the adjacent position.
 *
 * Example
 Given matrix:
   [doaf
    agai
    dcan]
 and dictionary:
 {"dog", "dad", "dgdg", "can", "again"}

 return {"dog", "dad", "can", "again"}

 Ref Url: https://discuss.leetcode.com/topic/33246/java-15ms-easiest-solution-100-00
 Base Question {@link com.advanced.dataStructure.backTrack.WordSearch}

 For this quesiton, solution need Trie + BackTrack(DFS)
 Intuitively, start from every cell and try to build a word in the dictionary. Backtracking (dfs) is the powerful way to exhaust every possible ways. Apparently, we need to do pruning when current character is not in any word.

 How do we instantly know the current character is invalid? HashMap?
 How do we instantly know what's the next valid character? LinkedList?
 But the next character can be chosen from a list of characters. "Mutil-LinkedList"?
 Combing them, Trie is the natural choice. Notice that:

 TrieNode is all we need. search and startsWith are useless.
 No need to store character at TrieNode. c.next[i] != null is enough.
 Never use c1 + c2 + c3. Use StringBuilder.
 No need to use O(n^2) extra space visited[m][n].
 No need to use StringBuilder. Storing word itself at leaf node is enough.
 No need to use HashSet to de-duplicate. Use "one time search" trie.
 */
public class WordSearchAdvanced {

    /**
     * url: https://en.wikipedia.org/wiki/Trie
     */
    public TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w: words) {
            TrieNode p = root;
            for (char c: w.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) {
                    p.next[i] = new TrieNode();
                }
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }

    public List<String> findWords(char[][] board, String[] dict) {
        List<String> res = new ArrayList<>();
        if (isNotValid(board, dict)) {
            return res;
        }
        // build Trie first
        TrieNode root = buildTrie(dict);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, res);
            }
        }
        return res;
    }
    // private assistant direction array
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    private void dfs(char[][] board, int i, int j, TrieNode root, List<String> res) {
        // record original char value, later will override in recursion, then copy back when quit recursion
        char c = board[i][j];
        if (c == '#' || root.next[c - 'a'] == null) {
            return;
        }

    }


    private boolean isNotValid(char[][] board, String[] dict) {
        if (board == null || board.length == 0 || dict == null || dict.length == 0) {
            return true;
        }
        return false;
    }

}

class TrieNode {
    TrieNode[] next = new TrieNode[26];
    String word;
}