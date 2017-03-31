package com.leetcode.linkedlist;

/**
 * Created by charles on 2/27/17.
 * Reverse a singly linked list.

 click to show more hints.

 Hint:
 A linked list can be reversed either iteratively or recursively. Could you implement both?
 */
public class ReverseLinkedList_208 {
    /**
     * Time complexity : O(n)O(n). Assume that nn is the list's length, the time complexity is O(n), Space is O(1)
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode prev = null;
        ListNode tmp;

        while (head != null) {
            // record next value of current node before reversing, otherwise will lose info
            tmp = head.next;
            head.next = prev;
            prev = head;
            head = tmp;
        }
        return prev;
    }

    /** recursion
     * The recursive version is slightly trickier and the key is to work backwards. Assume that the rest of the list had already been reversed, now how do I reverse the front part?
     * Let's assume the list is:
     * n1 → … → nk-1 → nk → nk+1 → … → nm → Ø
     * Assume from node nk+1 to nm had been reversed and you are at node nk.
     * n1 → … → nk-1 → nk → nk+1 ← … ← nm
     * We want nk+1’s next node to point to nk.
     *
     * So : nk.next.next = nk;
     * Be very careful that n1's next must point to Ø. If you forget about this, your linked list has a cycle in it. This bug could be caught if you test your code with a linked list of size 2.
     *
     * Time complexity : O(n)O(n). Assume that nn is the list's length, the time complexity is O(n)O(n).
     * Space complexity : O(n)O(n). The extra space comes from implicit stack space due to recursion. The recursion could go up to nn levels deep.
     * */
    public ListNode reverseListRecur(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = reverseListRecur(head.next);
        head.next.next = head;
        head.next = null;
        return prev;
    }
}
