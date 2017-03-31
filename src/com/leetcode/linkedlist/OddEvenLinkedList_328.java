package com.leetcode.linkedlist;

/**
 * Created by charles on 12/21/16.
 * Given a singly linked list, group all odd nodes together followed by the even nodes.
 * Please note here we are talking about the node number and not the value in the nodes.
 You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.

 Example:
 Given 1->2->3->4->5->NULL,
 return 1->3->5->2->4->NULL.
 */
public class OddEvenLinkedList_328 {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        // odd/ even is judged by index of list instead of value of node
        ListNode odd = head, even = head.next;
        ListNode evenHead = even;
        // independently build odd list and even list
        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }
        // attach even node list after odd node list
        odd.next = evenHead;

        return head;
    }

    public static void main(String[] args) {
        OddEvenLinkedList_328 o = new OddEvenLinkedList_328();
        int[] nums = {1,4,2,8,3,5,6,7};
        ListNode head = ListNodeUtil.convert(nums);
        ListNodeUtil.print(head);

        ListNode res = o.oddEvenList(head);
        ListNodeUtil.print(res);
    }
}
