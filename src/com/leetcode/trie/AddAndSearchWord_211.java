package com.leetcode.trie;

import com.leetcode.tree.TreeNode;

/**
 * Created by charles on 4/29/17.
 * Design a data structure that supports the following two operations:

 void addWord(word)
 bool search(word)
 search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.

 For example:

 addWord("bad")
 addWord("dad")
 addWord("mad")
 search("pad") -> false
 search("bad") -> true
 search(".ad") -> true
 search("b..") -> true
 */
public class AddAndSearchWord_211 {
    /** because assuming all words are consist of lowercase letter a-z
     * addWord method same as insert() in {@link ImplementTrie_208}
     */
    private TrieNode root;

    public AddAndSearchWord_211() {
        this.root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode node = root;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            if (!node.containsKey(c)) {
                node.put(c, new TrieNode());
            }
            node = node.get(c);
        }
        node.setEnd();
    }
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     * generally same in {@link ImplementTrie_208}
     * use backtrack to search matching word for "."
     * */
    public boolean search(String word) {
        return helper(word.toCharArray(), 0, root);
    }
    private boolean helper(char[] chars, int idx, TrieNode node) {
        if (idx == chars.length) {
            return node.isEnd();
        }
        if (chars[idx] != '.') {
            return node.containsKey(chars[idx]) && helper(chars, idx+1, node.get(chars[idx]));
        } else {
            for (int i = 0; i < 26; i++) {
                if (node.containsKey(chars[i])
                        && helper(chars, idx + 1, node.get(chars[i]))) {
                    return true;
                }
            }
        }
        return false;
    }
}

