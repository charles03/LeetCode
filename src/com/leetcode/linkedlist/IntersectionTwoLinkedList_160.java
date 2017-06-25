package com.leetcode.linkedlist;

/**
 * Created by charles on 4/10/17.
 * Write a program to find the node at which the intersection of two singly linked lists begins.


 For example, the following two linked lists:

 A:          a1 → a2
                     ↘
                       c1 → c2 → c3
                     ↗
 B:     b1 → b2 → b3
 begin to intersect at node c1.


 Notes:

 If the two linked lists have no intersection at all, return null.
 The linked lists must retain their original structure after the function returns.
 You may assume there are no cycles anywhere in the entire linked structure.
 Your code should preferably run in O(n) time and use only O(1) memory.
 Credits:
 */
public class IntersectionTwoLinkedList_160 {
    public ListNode getIntersectionNode(ListNode head1, ListNode head2) {
        ListNode node1 = head1;
        ListNode node2 = head2;
        int diff = getListLength(node1) - getListLength(node2);
        node1 = head1;
        node2 = head2;
        if (diff < 0) {
            while (diff < 0) {
                node2 = node2.next;
                diff++;
            }
        } else {
            while (diff > 0) {
                node1 = node1.next;
                diff--;
            }
        }
        while (node1 != null && node2 != null) {
            if (node1.getVal() == node2.getVal()) {
                return node1;
            }
            node1 = node1.next;
            node2 = node2.next;
        }
        return null;
    }
    public int getListLength(ListNode head) {
        int len = 0;
        while (head != null) {
            head = head.next;
            len++;
        }
        return len;
    }

    /**
     * Maintain two pointers pA and pB initialized at the head of A and B, respectively. Then let them both traverse through the lists, one node at a time.
     When pA reaches the end of a list, then redirect it to the head of B (yes, B, that's right.); similarly when pB reaches the end of a list, redirect it the head of A.
     If at any point pA meets pB, then pA/pB is the intersection node.
     */
    public ListNode getIntersectionNodeII(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        ListNode a = head1;
        ListNode b = head2;

        while (a != b) {
            // when either A/B reach end of list, need to redirect to the other list
            // to start second iteration.
            if (a == null) {
                a = head2;
            } else {
                a = a.next;
            }
            if (b == null) {
                b = head1;
            } else {
                b = b.next;
            }
        }
        return a;
    }
}
