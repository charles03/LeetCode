package com.leetcode.linkedlist;

/**
 * Created by charles on 4/1/17.
 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
 */
public class MergeTwoSortedList_21 {
    /**
     * Thought:
     * create dummy node, then compare head of each list
     * then assign dummy.next to compare result
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;

        while (l1 != null && l2 != null) {
            if (l1.getVal() < l2.getVal()) {
                head.next = l1;
                l1 = l1.next;
            } else {
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        if (l1 != null) {
            head.next = l1;
        }
        if (l2 != null) {
            head.next = l2;
        }
        return dummy.next;
    }

    /** Recursion Solution
     * everytime, find smaller one as merge head
     * meanwhile that small one is last recursive procession's next code
     */
    public ListNode mergeTwoListsII(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.getVal() < l2.getVal()) {
            l1.next = mergeTwoListsII(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoListsII(l1, l2.next);
            return l2;
        }
    }
}
