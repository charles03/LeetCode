package com.leetcode.design;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 6/3/17.
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

 get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

 Follow up:
 Could you do both operations in O(1) time complexity?

 LRUCache cache = new LRUCache( 2 /capacity/ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
 */
public class LRUcache_146 {
    /**
     * key is to use doubly linked list which enable to quickly move nodes
     * LRU cache, need map to store key/val pair make O(1) for get()
     * doubly linked nodes make node adding/removal operation O(1)
     *
     * define doubly linked list,
     * head.next is least recently used,
     * tail.prev is most recently used
     */
    private int capacity;
    private Map<Integer, Node> cache = new HashMap<>();
    private Node head = new Node(-1,-1); // dummy node, start of doubly list
    private Node tail = new Node(-1,-1); // dummy node, end of doubly list

    public LRUcache_146(int capacity) {
        this.capacity = capacity;
        // init relation of tail and head;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node curr = cache.get(key);
        int res = curr.value;
        removeNode(curr);
        addToTail(curr);
        return res;
    }

    public void put(int key, int value) {
        if (get(key) != -1) {
            cache.get(key).value = value; // update value in node under key
            return;
        }
        // insert new node into cache
        Node lruNode = null;
        if (cache.size() == capacity) {
            lruNode = head.next;
            cache.remove(lruNode.key);
            // manual remove node from doubly linked list
            removeNode(lruNode);
        }
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addToTail(newNode);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    // insert current node between previous node of tail and tail
    private void addToTail(Node node) {
        node.prev = tail.prev;
        tail.prev = node;
        node.prev.next = node;
        node.next = tail;
    }

    private class Node {
        int key;
        int value;
        Node prev;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
}
