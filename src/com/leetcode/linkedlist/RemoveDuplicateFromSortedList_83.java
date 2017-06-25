package com.leetcode.linkedlist;

import java.util.List;

/**
 * Created by charles on 4/1/17.
 * Given a sorted linked list, delete all duplicates such that each element appear only once.

 For example,
 Given 1->1->2, return 1->2.
 Given 1->1->2->3->3, return 1->2->3.

 */
public class RemoveDuplicateFromSortedList_83 {
    /**
     * Noted: indefinite loop if without head = head.next
     * First assign head to dummy node,
     * do not work on head directly,
     * then return head again
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode node = head;
        while (node != null && node.next != null) {
            if (node.getVal() == node.next.getVal()) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;
    }
}
