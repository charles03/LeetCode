package com.leetcode.linkedlist;

/**
 * Created by charles on 12/22/16.
 * Remove all elements from a linked list of integers that have value val.

 Example
 Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6
 Return: 1 --> 2 --> 3 --> 4 --> 5
 */
public class RemoveLinkedListElements_203 {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        /** because head may get removed, thus it needs dummy node */
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        node.next = head;
        while (node.next != null) {
            if (node.next.getVal() == val) {
                node.next = node.next.next;
            } else { // must put into else block
                node = node.next;
            }
        }
        return dummy.next;
    }

    public ListNode removeElementsByRecursion(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        head.next = removeElementsByRecursion(head.next, val);
        return head.getVal() == val ? head.next : head;
    }

    public static void main(String[] args) {
        RemoveLinkedListElements_203 r = new RemoveLinkedListElements_203();
        int[] num1 = {1,2,3,4,5,6};
        ListNode head = ListNodeUtil.convert(num1);
        ListNode head2 = r.removeElements(head, 1);
        ListNodeUtil.print(head2);
        ListNodeUtil.print(r.removeElements(head2, 3));

        int[] num2 = {1};
        ListNode head3 = ListNodeUtil.convert(num2);
        ListNodeUtil.print(r.removeElements(head3, 1));
    }
}
