package com.advanced.dataStructure2.heap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by charles on 10/30/16.
 * For Question {@link SlidingWindowMedianByHashHeap}
 */
public class HashHeap {
    private ArrayList<Integer> heap;
    private String mode;
    private int size_t;
    private HashMap<Integer, Node> hash;

    public HashHeap(String mode) {
        // "MIN" -> min heap
        // "MAX" -> max heap
        // use ArrayList to store data for heap
        this.heap = new ArrayList<Integer>();
        this.mode = mode;
        this.hash = new HashMap<Integer, Node>();
        this.size_t = 0;
    }
    /** not remove, only return value of peek element*/
    public int peek() {return heap.get(0);}
    /** size of Hash Heap */
    public int size() {return size_t;}
    /** boolean to check empty() or not */
    public boolean isEmpty() {return (heap.size() == 0);}

    /** return top element and remove from heap */
    public Integer poll() {
        size_t--;
        Integer current = heap.get(0);
        Node  currentNode= hash.get(current);

        if (currentNode.count == 1) { // only one unique data, no duplicate
            /** swap first and last, then sift down value after swapped */
            swap(0, heap.size() - 1);
            // remove last one in both heap and hash map
            hash.remove(current);
            heap.remove(heap.size() - 1);
            /** sift down top element if heap is not empty */
            if (heap.size() > 0) {
                siftdown(0);
            }
        } else { // if has duplicate
            // set count - 1 for this current peek node
            hash.put(current, new Node(0, currentNode.count - 1));
        }
        return current;
    }
    /** add new integer element into HashHeap */
    public void add(int current) {
        size_t++;
        // include duplication scenario
        if (hash.containsKey(current)) {
            Node currentNode = hash.get(current);
            hash.put(current, new Node(currentNode.id, currentNode.count + 1));
        } else {
            heap.add(current);
            hash.put(current, new Node(heap.size() - 1, 1));
        }
        siftup(heap.size() - 1);
    }

    /** remove value from HashHeap in O(lgn) time complexity */
    public void delete(int current) {
        size_t--;
        Node currentNode = hash.get(current);
        int id = currentNode.id;
        int count = currentNode.count;
        if (count == 1) {
            swap(id, heap.size() - 1);
            hash.remove(current);
            heap.remove(heap.size() - 1);
            if (heap.size() > id) {
                // do sift in all array
                siftup(id);
                siftdown(id);
            }
        } else {
            hash.put(current, new Node(id, count - 1));
        }
    }
    /* swap elements of index A and index B, need update for array heap and hash map*/
    private void swap(int idA, int idB) {
        int valA = heap.get(idA);
        int valB = heap.get(idB);
        int countA = hash.get(valA).count;
        int countB = hash.get(valB).count;
        // for element B, assign index of original element A to B, vise versa
        hash.put(valB, new Node(idA, countB));
        hash.put(valA, new Node(idB, countA));
        // swap for heap array
        heap.set(idA, valB);
        heap.set(idB, valA);
    }

    /* for current index, re-organize so that match criteria
       1. min heap (parent is less than both left and right)
       2. max heap (parent is larger than both left and right)
     */
    private void siftdown(int id) {
        int leftIndex = leftChild(id);
        int rightIndex = 0;
        int son = 0;
        while (leftIndex < heap.size()) {
            rightIndex = rightChild(id);
            if (rightIndex >= heap.size() || compareWith(heap.get(leftIndex), heap.get(rightIndex))) {
                son = leftIndex;
            } else {
                son = rightIndex;
            }
            if (compareWith(heap.get(id), heap.get(son))) {
                // already match criteria
                break;
            } else {
                // swap to continue;
                swap(id, son);
            }
            id = son;
            leftIndex = leftChild(id);
        }
    }
    /* handle min heap and max heap: shift current id to parent id if under conditions */
    private void siftup(int id) {
        int pid = parent(id);
        while (pid > -1) {
            if (compareWith(heap.get(pid), heap.get(id))) {
                break;
            } else {
                swap(id, pid);
            }
            id = pid;
            pid = parent(id);
        }
    }

    /** two scenarios, expect (a <= b) for min heap (a > b) for max heap */
    private boolean compareWith(int a, int b) {
        boolean match = false;
        switch (this.mode) {
            case "MIN" : match = compareForMinHeap(a, b);
                break;
            case "MAX" : match = compareForMaxHeap(a, b);
                break;
            default : throw new RuntimeException("Not give correct mode, so far support MIN and MAX");
        }
        return match;
    }
    private boolean compareForMinHeap(int a, int b) {
        return a <= b ? true : false;
    }
    private boolean compareForMaxHeap(int a, int b) {
        return a > b ? true : false;
    }


    /* return index of parent of current index in array */
    private int parent(int id) {return id == 0 ? -1 : (id - 1) / 2;}
    /* return index of left child of current index in array */
    private int leftChild(int id) {return id * 2 + 1;}
    /* return index of right child of current index in array */
    private int rightChild(int id) {return id * 2 + 2;}
}

class Node {
    public Integer id; // index of heap array
    public Integer count; // count of same value in heap array
    Node(Node now) {
        id = now.id;
        count = now.count;
    }
    /** wrap count field into object, in order to handle duplicates */
    Node(Integer id, Integer count) {
        this.id = id;
        this.count = count;
    }
}