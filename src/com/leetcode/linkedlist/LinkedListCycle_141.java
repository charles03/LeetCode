package com.leetcode.linkedlist;

/**
 * Created by charles on 4/7/17.
 * Given a linked list, determine if it has a cycle in it.

 Follow up:
 Can you solve it without using extra space?
 */
public class LinkedListCycle_141 {
    /**
     * 1. use two pointers, slow and fast
     * 2. slow move step by step, whereas fast move two steps at time
     * 3. if list contains cycle, slow and fast will meet at some point
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;

        // use fast to determine while loop
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
