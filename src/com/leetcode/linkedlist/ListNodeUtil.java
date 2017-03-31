package com.leetcode.linkedlist;

/**
 * Created by charles on 12/21/16.
 */
public class ListNodeUtil {

    /** Convert array input to List with ListNode */
    public static ListNode convert(int[] nums) {
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;

        for (int i = 0; i < nums.length; i++) {
            node.next = new ListNode(nums[i]);
            node = node.next;
        }
        return dummy.next;
    }

    /** Output whole list */
    public static void print(ListNode head) {
        ListNode node = head;
        while (node != null) {
            System.out.print(node.getVal() + " -> ");
            node = node.next;
        }
        System.out.println();
    }

    /** reverse list */
    public static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode tmp = head.next; // keep original next node info
            head.next = prev; // do reverse
            prev = head; // move to next;
            head = tmp; // head point to original next in list
        }
        return prev;
    }
}
