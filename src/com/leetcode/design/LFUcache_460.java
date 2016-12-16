package com.leetcode.design;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by charles on 11/22/16.
 * http://stackoverflow.com/questions/17759560/what-is-the-difference-between-lru-and-lfu
 *
 * Design and implement a data structure for Least Frequently Used (LFU) cache.
 * It should support the following operations: get and set.

 get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 set(key, value) - Set or insert the value if the key is not already present.
    When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item.
    For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

 Follow up:
 Could you do both operations in O(1) time complexity?

 LFUCache cache = new LFUCache( 2 // capacity // );

        cache.set(1, 1);
        cache.set(2, 2);
        cache.get(1);       // returns 1
        cache.set(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.get(3);       // returns 3.
        cache.set(4, 4);    // evicts key 1.
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
 */
public class LFUcache_460 {
    /**
     * Thought: runtime complexity of O(1) for each of dictionary operations
     * (insertion, lookup and deletion)
     * achieved by maintaining 2 linked lists
     * one on the access frequency and one for all elements that have same access frequency
     * A hash table is used to access elements by key
     * A doubly linked list is used to link together nodes which represent a set of nodes
     * that have same access frequency
     * frequency list and node list
     * Each node in node list has pointer to its parent node in the frequency list
     *
     * above is http://dhruvbird.com/lfu.pdf
     *  * LFU cache implementation based on http://dhruvbird.com/lfu.pdf, with some notable differences:
     * <ul>
     * <li>
     * Frequency list is stored as an array with no next/prev pointers between nodes: looping over the array should be faster and more CPU-cache friendly than
     * using an ad-hoc linked-pointers structure.
     * </li>
     * <li>
     * The max frequency is capped at the cache size to avoid creating more and more frequency list entries, and all elements residing in the max frequency entry
     * are re-positioned in the frequency entry linked set in order to put most recently accessed elements ahead of less recently ones,
     * which will be collected sooner.
     * </li>
     * <li>
     * The eviction factor determines how many elements (more specifically, the percentage of) will be evicted.
     * </li>
     * </ul>
     * As a consequence, this cache runs in *amortized* O(1) time (considering the worst case of having the lowest frequency at 0 and having to evict all
     * elements).
     */

    /**
     * http://javarticles.com/2012/06/lfu-cache.html
     */
    private Map<Integer, CacheNode> cache;
    // linkedHashSet array are equal to doubly linked lists
    // horizontal from 0 to capacity, is frequency
    // vertical is each node linked to frequency
    private final LinkedHashSet[] frequencyList;
    private int lowestFrequency;
    private int maxFrequency;

    private final int maxCacheSize;
    private final double evictionFactor;

    public LFUcache_460(int capacity) {
        this(capacity, 0.75);
    }

    public LFUcache_460(int capacity, double evictionFactor) {
        if (evictionFactor <= 0 || evictionFactor >= 1) {
            throw new IllegalArgumentException("Eviction factor must be between 0 and 1");
        }
        this.cache = new HashMap<>(capacity);
        this.frequencyList = new LinkedHashSet[capacity];
        this.lowestFrequency = 0;
        this.maxFrequency = capacity - 1;
        this.maxCacheSize  = capacity;
        this.evictionFactor = evictionFactor;

        initFrequencyList();
    }

    public int get(int key) {
        CacheNode currentNode = cache.get(key);
        if (currentNode != null) {
            // increment frequency
            updateFrequency(currentNode);
            return currentNode.value;
        } else {
            return -1;
        }
    }

    public void set(int key, int value) {
        CacheNode currentNode = cache.get(key);
        if (currentNode == null) { // this new node
            if (cache.size() == maxCacheSize) { // capacity in map is full
                doEviction(); // remove LFU nodes, amount is capacity * evictionFactor
            }
            LinkedHashSet<CacheNode> nodes = frequencyList[0];
            currentNode = new CacheNode(key, value, 0);
            // insertion
            cache.put(key, currentNode);
            nodes.add(currentNode);
            // update lowest frequency
            lowestFrequency = 0;
        } else {
            currentNode.value = value;
            // if node already exist, then need update frequency
            updateFrequency(currentNode);
        }
    }

    // assign memory space for eqch frequency list
    private void initFrequencyList() {
        for (int i = 0; i <= maxFrequency; i++) {
            frequencyList[i] = new LinkedHashSet<CacheNode>();
        }
    }

    // update frequency list, move current node to corresponding new frequency
    private void updateFrequency(CacheNode currentNode) {
        int currentFrequency = currentNode.frequency;
        if (currentFrequency < maxFrequency) {
            int nextFrequency = currentFrequency + 1;
            LinkedHashSet<CacheNode> nodesInCurrentFrequency = frequencyList[currentFrequency];
            LinkedHashSet<CacheNode> nodesInNextFrequency = frequencyList[nextFrequency];

            moveCurrentNodeToNextFrequency(currentNode, nextFrequency, nodesInCurrentFrequency, nodesInNextFrequency);
            // re-insert node back to hash table after node.frequency updated
            cache.put(currentNode.key, currentNode);
            // updat lowestFrequency if necessary when in below condition
            if (lowestFrequency == currentFrequency && nodesInCurrentFrequency.isEmpty()) {
                lowestFrequency = nextFrequency;
            }
        } else { // if already reach maxFrequency

            LinkedHashSet<CacheNode> nodesInMaxFrequency = frequencyList[currentFrequency];
            // Hybrid with LRU: put most recently accessed ahead of others
            nodesInMaxFrequency.remove(currentNode);
            nodesInMaxFrequency.add(currentNode);
        }
    }
    // update frequency of currentNode with next frequency
    private void moveCurrentNodeToNextFrequency(CacheNode currentNode, int nextFreq, LinkedHashSet<CacheNode> currentNodes, LinkedHashSet<CacheNode> newNodes) {
        currentNodes.remove(currentNode);
        newNodes.add(currentNode);
        currentNode.frequency = nextFreq;
    }

    // evict certail amount of nodes, target amount is maxCacheSize * evictionFactor
    private void doEviction() {
        int currentDeletedAmount = 0;
        double targetAmount = maxCacheSize * evictionFactor;
        while (currentDeletedAmount < targetAmount) {
            LinkedHashSet<CacheNode> nodesInLowestFrequency = frequencyList[lowestFrequency];
            if (nodesInLowestFrequency.isEmpty()) {
                throw new IllegalStateException("Lowest frequency contraint violated");
            }
            Iterator<CacheNode> iter = nodesInLowestFrequency.iterator();
            while (iter.hasNext() && currentDeletedAmount++ < targetAmount) {
                CacheNode node = iter.next();
                iter.remove(); // sequentially remove node until reach target amount
                cache.remove(node.key); // remove node in hash map
            }
            if (!iter.hasNext()) {
                // update lowest frequency in instance field
                findNextLowestFrequency();
            }
        }
    }

    private void findNextLowestFrequency() {
        // find first hashset with node, that frequency is lowest frequency
        while (lowestFrequency <= maxFrequency && frequencyList[lowestFrequency].isEmpty()) {
            lowestFrequency++;
        }
        // re-cycle
        if (lowestFrequency > maxFrequency) {
            lowestFrequency = 0;
        }
    }
}

class CacheNode {
    public final int key;
    public int value;
    public int frequency;

    public CacheNode(int key, int value, int frequency) {
        this.key = key;
        this.value = value;
        this.frequency = frequency;
    }
}
