package com.leetcode.twopointer;

import com.leetcode.linkedlist.ListNode;

/**
 * Created by charles on 4/16/17.
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

 Note: Do not modify the linked list.

 Follow up:
 Can you solve it without using extra space?
 */
public class LinkedListCycleII_142 {
    /**
     * When fast and slow meet at point p, the length they have run are 'a+2b+c' and 'a+b'.
     Since the fast is 2 times faster than the slow. So a+2b+c == 2(a+b), then we get 'a==c'.
     So when another slow2 pointer run from head to 'q', at the same time, previous slow pointer will run from 'p' to 'q', so they meet at the pointer 'q' together.
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                fast = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }

        return null;
    }
}
