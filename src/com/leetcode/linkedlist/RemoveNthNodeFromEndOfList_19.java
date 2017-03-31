package com.leetcode.linkedlist;

/**
 * Created by charles on 12/21/16.
 * Given a linked list, remove the nth node from the end of list and return its head.
 For example,
 Given linked list: 1->2->3->4->5, and n = 2.
 After removing the second node from the end, the linked list becomes 1->2->3->5.
 Note:
 Given n will always be valid.
 Try to do this in one pass.
 */
public class RemoveNthNodeFromEndOfList_19 {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode ahead = dummy;
        ListNode behind = dummy;
        ahead.next = head;
        while (n >= 0 && ahead != null) {
            ahead = ahead.next;
            n--;
        }
        if (n >= 0) { // when n invalid
            return head;
        }
        while (ahead != null) {
            ahead = ahead.next;
            behind = behind.next;
        }
        // skip the desired node
        behind.next = behind.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        RemoveNthNodeFromEndOfList_19 r = new RemoveNthNodeFromEndOfList_19();

        int[] num1 = {1,2,3,4};

        int[] num2 = {42,33,13,4,40,26,29,34,37,19,3,32,17,33,32,21,39,30,29,4,9,49,43,40,45,36,5,26,0,38};
        ListNodeUtil.print(r.removeNthFromEnd(ListNodeUtil.convert(num1),3));
        ListNodeUtil.print(r.removeNthFromEnd(ListNodeUtil.convert(num1),2));
        ListNodeUtil.print(r.removeNthFromEnd(ListNodeUtil.convert(num1),4));

        ListNodeUtil.print(r.removeNthFromEnd(ListNodeUtil.convert(num2),26));
        ListNodeUtil.print(r.removeNthFromEnd(ListNodeUtil.convert(num2),2));
        ListNodeUtil.print(r.removeNthFromEnd(ListNodeUtil.convert(num2),4));
    }
}
