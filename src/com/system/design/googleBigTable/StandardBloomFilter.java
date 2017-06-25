package com.system.design.googleBigTable;

import java.util.BitSet;
import java.util.List;

/**
 * Created by charles on 9/18/16.
 *
 * Implement a standard bloom filter. Support the following method:
 1. StandardBloomFilter(k),The constructor and you need to create k hash functions.
 2. add(string). add a string into bloom filter.
 3. contains(string). Check a string whether exists in bloom filter.

 Example
 StandardBloomFilter(3)
 add("lint")
 add("code")
 contains("lint") // return true
 contains("world") // return false
 */
public class StandardBloomFilter {
    private BitSet bits;
    private int k;
    private List<HashFunction> hashList;
    private int BASE = 100000;

    public StandardBloomFilter(int k) {
        // initialize your data structure here
        this.k = k;
        for (int i = 0; i < k; i++) {
            hashList.add(new HashFunction(BASE + i, 2 * i + 3));
        }
        bits = new BitSet(BASE + k);
    }

    public void add(String word) {
        int position = 0;
        for (int i = 0; i < k; i++) {
            position = hashList.get(i).hash(word);
            bits.set(position);
        }
    }

    public boolean contains(String word) {
        int position = 0;
        for (int i = 0; i < k; i++) {
            position = hashList.get(i).hash(word);
            if (!bits.get(position)) {
                return false;
            }
        }
        return true;
    }
}
