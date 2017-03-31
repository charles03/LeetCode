package com.leetcode.trie;

import com.leetcode.tree.TreeNode;

/**
 * Created by charles on 3/1/17.
 * Implement a trie with insert, search, and startsWith methods.

 Note:
 You may assume that all inputs are consist of lowercase letters a-z.
 */
public class ImplementTrie_208 {
    private TrieNode root;

    /** Initialize your data structure here. */
    public ImplementTrie_208() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    /**
     * O(m) Time, wher em is key length
     * In each iteration of the algorithm, we either examine or create a node in the trie till we reach the end of the key. This takes only mm operations.
     * O(m) Space
     * In the worst case newly inserted key doesn't share a prefix with the the keys already inserted in the trie. We have to add mm new nodes, which takes us O(m)O(m) space.
     */
    public void insert(String word) {
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

    /** Returns if the word is in the trie. */
    /**
     * Each key is represented in the trie as a path from the root to the internal node or leaf.
     * We start from the root with the first key character. We examine the current node for a link corresponding to the key character. There are two cases :
     * A link exist. We move to the next node in the path following this link, and proceed searching for the next key character.
     * A link does not exist. If there are no available key characters and current node is marked as isEnd we return true. Otherwise there are possible two cases in each of them we return false :
     *  There are key characters left, but it is impossible to follow the key path in the trie, and the key is missing.
     *  No key characters left, but current node is not marked as isEnd. Therefore the search key is only a prefix of another key in the trie.
     */
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }

    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            if (node.containsKey(c)) {
                node = node.get(c);
            } else {
                return null;
            }
        }
        return node;
    }

    /** Returns if there is any word in the trie that starts with the given prefix.
     * O(m) time, O(1) space */
    /**
     * The approach is very similar to the one we used for searching a key in a trie. We traverse the trie from the root, till there are no characters left in key prefix or it is impossible to continue the path in the trie with the current key character.
     * The only difference with the mentioned above search for a key algorithm is that when we come to an end of the key prefix, we always return true. We don't need to consider the isEnd mark of the current trie node, because we are searching for a prefix of a key, not for a whole key.
     */
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}

/**
 * Trie is a rooted tree. Its nodes have the following fields:

 Maximum of RR links to its children, where each link corresponds to one of RR character values from dataset alphabet.
 In this article we assume that RR is 26, the number of lowercase latin letters.
 Boolean field which specifies whether the node corresponds to the end of the key, or is just a key prefix.
 */
class TrieNode {
    private TrieNode[] links;

    private final int SIZE = 26;

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[SIZE];
    }
    public boolean containsKey(char ch) {
        return links[ch - 'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch - 'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch - 'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}