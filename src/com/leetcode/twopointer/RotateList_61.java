package com.leetcode.twopointer;

import com.leetcode.linkedlist.ListNode;
import com.leetcode.linkedlist.ListNodeUtil;

/**
 * Created by charles on 4/13/17.
 * Given a list, rotate the list to the right by k places, where k is non-negative.

 For example:
 Given 1->2->3->4->5->NULL and k = 2,
 return 4->5->1->2->3->NULL.
 */
public class RotateList_61 {
    /**
     * Because k can be over length of list, thuse, should set k = k % len
     * and then move pointer from head len - k step
     * then rotate node by setting new relationship of next
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        ListNode node = head;
        int len = 1;
        while (node.next != null) {
            node = node.next;
            len++;
        }
        node.next = head; // set it back
        k %= len; // to make sure k < len
        for (int i = len - k; i > 1; i--) {
            head = head.next;
        }
        // set new relationship
        node = head.next;
        head.next = null;
        return node;
    }

    /** without use counting length,
     * trick is when k > length, set fast pointer back to head;
     * This implementation has bad performance
     * when k is extreme large
     */
    public ListNode rotateRightII(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;
        ListNode newHead;
        for (int i = 0; i < k; i++) {
            if (fast.next == null) {
                fast = head;
            } else {
                fast = fast.next;
            }
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        fast.next = head; // at tail
        newHead = slow.next; // break point as new head;
        slow.next = null; // next of prev set to null
        return newHead;
    }

    public static void main(String[] args) {
        RotateList_61 r = new RotateList_61();
        ListNode n1 = new ListNode(1);
        n1.next = null;
        System.out.println(r.rotateRight(n1, 1).getVal());

        int[] a1 = {1,2,3};
        ListNode r1 = ListNodeUtil.convert(a1);
        System.out.println(r.rotateRightII(r1, 200000000).getVal());
    }
}
