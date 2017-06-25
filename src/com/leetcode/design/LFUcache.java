package com.leetcode.design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by charles on 6/1/17.
 * {@link LFUcache_460}
 * difference between LRU & LFU
 * Least Recently Used (LRU) cache with a HashMap + doubly linked list implementation with O(1) eviction time and O(1) load time
 * Least Frequently Used (LFU) cache takes advantage of this information by keeping track of how many times the cache request has been used in its eviction algorithm.
 */
public class LFUcache {
    /**
     * solution:
     * need map to store key-value pair via set method
     * need map to store info of frequency, value is count, key is given input
     * also need map to store doubly link list data structure, key is frequency, value is list contains all keys under current frequency
     *
     *
     * Better implemenation including LRU and LFU
     *          https://my.oschina.net/u/866190/blog/188712
     * reference
     *          http://blog.csdn.net/yunhua_lee/article/details/7599671
     */
    private Map<Integer, Integer> vals;
    private Map<Integer, Integer> freqs;
    private Map<Integer, LinkedHashSet<Integer>> doublys; // key is freq, value is doubly linked list which contain given input keys

    private int capacity;
    private int leastFreq = -1;

    public LFUcache(int capacity) {
        this.capacity = capacity;
        vals = new HashMap<>(capacity);
        freqs = new HashMap<>(capacity);
        doublys = new HashMap<>(capacity);

        doublys.put(1, new LinkedHashSet<>());
    }

    public void set(int key, int value) {
        if (capacity <= 0) {
            return;
        }
        if (vals.containsKey(key)) {
            vals.put(key, value); // update key-val pair if existed
            // call get(key) to increase freq
            updateFreq(key);
            return;
        }
        if (vals.size() >= capacity) {
            evict();
        }
        vals.put(key, value);
        freqs.put(key, 1);
        leastFreq = 1;
        doublys.get(1).add(key);
    }
    public int get(int key) {
        if (!vals.containsKey(key)) {
            return -1;
        }
        updateFreq(key);
        return vals.get(key);
    }
    private void updateFreq(int key) {
        // update frequency map
        int freq = freqs.get(key);
        int newFreq = freq + 1;
        freqs.put(key, freq + 1);
        // remove node in doubly linked list under relating frequency
        doublys.get(freq).remove(key);
        // update least frequency when curr freq before update is equal, and also doubly list is empty after remove that node
        if (freq == leastFreq && doublys.get(freq).size() == 0) {
            leastFreq++;
        }
        if (!doublys.containsKey(newFreq)) {
            // when no newFreq in the map
            LinkedHashSet<Integer> doubly = new LinkedHashSet<>();
            doubly.add(key);
            doublys.put(newFreq, doubly);
        }
        // if already have this new Frequency
        doublys.get(newFreq).add(key);
    }
    private void evict() {
        LinkedHashSet<Integer> doubly = doublys.get(leastFreq);
        int key = doubly.iterator().next();
        doubly.remove(key);
        vals.remove(key);
    }
}
