package com.leetcode.trie;

/**
 * Trie is a rooted tree. Its nodes have the following fields:

 Maximum of RR links to its children, where each link corresponds to one of RR character values from dataset alphabet.
 In this article we assume that RR is 26, the number of lowercase latin letters.
 Boolean field which specifies whether the node corresponds to the end of the key, or is just a key prefix.
 */
public class TrieNode {
    private TrieNode[] links;
    private final int SIZE = 26;
    private boolean isEnd;
    private int index;

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
    public void setIndex(int index) { this.index = index; }
    public int getIndex() { return index; }
}
