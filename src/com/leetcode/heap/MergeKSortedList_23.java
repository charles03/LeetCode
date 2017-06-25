package com.leetcode.heap;

import com.leetcode.linkedlist.ListNode;

import java.util.PriorityQueue;

/**
 * Created by charles on 5/29/17.
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 */
public class MergeKSortedList_23 {
    /**
     * use Priority queue to keep k size elem in min heap.
     * and to poll min node from heap to get next in it's list
     * besides, use dummy node because head of list is unconfirmed
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        PriorityQueue<ListNode> heap = new PriorityQueue<>(lists.length, (a,b) -> a.getVal() - b.getVal());
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        for (ListNode node : lists) {
            if (node != null) {
                heap.add(node);
            }
        }
        ListNode currHead = null;
        while (!heap.isEmpty()) {
            currHead = heap.poll();
            tail.next = currHead; // set next relation
            tail = currHead; // move tail to next node;
            if (currHead != null) {
                heap.add(currHead.next);
            }
        }
        return dummy.next;
    }
}
