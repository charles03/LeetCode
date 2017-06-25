package com.advanced.dataStructure.trie;

/**
 * Created by charles on 9/25/16.
 *
 * Design a data structure that supports the following two operations: addWord(word) and search(word)
 search(word) can search a literal word or a regular expression string containing only letters a-z or ..
 A . means it can represent any one letter.

 Notice
 You may assume that all words are consist of lowercase letters a-z.

 Example
 addWord("bad")
 addWord("dad")
 addWord("mad")
 search("pad")  // return false
 search("bad")  // return true
 search(".ad")  // return true
 search("b..")  // return true

 Tag: Trie
 */
public class    AddAndSearchWord {

    // Adds a word into the data structure.
    public void addWord(String word) {
        // Write your code here
    }

    // Returns if the word is in the data structure. A word could
    // contain the dot character '.' to represent any one letter.
    public boolean search(String word) {
        // Write your code here
        return false;
    }
}

// Your WordDictionary object will be instantiated and called as such:
// WordDictionary wordDictionary = new WordDictionary();
// wordDictionary.addWord("word");
// wordDictionary.search("pattern");