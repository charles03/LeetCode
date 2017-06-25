package com.system.design.googleBigTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 9/18/16.
 *
 * Implement a counting bloom filter. Support the following method:
 1. add(string). Add a string into bloom filter.
 2. contains(string). Check a string whether exists in bloom filter.
 3. remove(string). Remove a string from bloom filter.

 Example
 CountingBloomFilter(3)
 add("lint")
 add("code")
 contains("lint") // return true
 remove("lint")
 contains("lint") // return false
 */
public class CountingBloomFilter {
    private int[] bits;
    private int k;
    private List<HashFunction> hashList;
    private int BASE = 100000;

    /** to calculate k different hashes for single string*/
    public CountingBloomFilter(int k) {
        // initialize your data structure here
        this.k = k;
        hashList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            /** BASE and seed will be changed with number of times */
            hashList.add(new HashFunction(BASE + i, 2 * i + 3));
        }
        // that's why need to allocate BASE + k size memo
        bits = new int[BASE + k];
    }

    public void add(String word) { // set 1
        int position = 0;
        for (int i = 0; i < k; i++) {
            position = hashList.get(i).hash(word);
            bits[position] += 1;
        }
    }

    public void remove(String word) { // decrease at corresponding position
        int position = 0;
        for (int i = 0; i < k; i++) {
            position = hashList.get(i).hash(word);
            bits[position] -= 1;
        }
    }

    /** as long as at least one certain position, the hash is not matching
     * then this word must not be in the bit map */
    public boolean contains(String word) {
        int position = 0;
        for (int i = 0; i < k; i++) {
            position = hashList.get(i).hash(word);
            if (bits[position] <= 0) {
                return false;
            }
        }
        return true;
    }
}
class HashFunction {
    int capacity;
    int seed;
    /** hash need capacity and seed */
    public HashFunction(int capacity, int seed) {
        this.capacity = capacity;
        this.seed = seed;
    }
    public int hash(String source) {
        int hash = 0;
        for (char c : source.toCharArray()) {
            hash = hash * seed + c;
            hash %= capacity; // limit to range [0, capacity)
        }
        return hash;
    }
}