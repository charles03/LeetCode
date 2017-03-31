package com.leetcode.linkedlist;

/**
 * Created by charles on 12/21/16.
 * Given a singly linked list, determine if it is a palindrome.
 Follow up:
 Could you do it in O(n) time and O(1) space?
 */
public class PalindromeLinkedList_234 {
    /**
     * Solution thought: Reverse first half == second half
     * Phase 1 : reverse right half while finding middle
     * Phase 2 : Compare reversed first half with second half
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast != null) {
            // odd numbers of nodes : left right half smaller
            slow = slow.next;
        }
        // reverse right half
        slow = ListNodeUtil.reverse(slow);
        fast = head; // reset fast pointed to head

        while (slow != null) {
            if (fast.getVal() != slow.getVal()) {
                return false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }

    public boolean isPalindromeTest(ListNode head) {
        if (head == null) {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        ListNode temp;
        ListNode prev = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            // reverse left half
            temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
        }
        // when odd number of element, move slow pointer right one step, to pass mid
        if (fast != null) {
            slow = slow.next;
        }
        // compare from mid to both head and tail
        while (prev != null) {
            if (prev.getVal() != slow.getVal()) {
                return false;
            }
            prev = prev.next;
            slow = slow.next;
        }
        return true;
    }

    public static void main(String[] args) {
        PalindromeLinkedList_234 p = new PalindromeLinkedList_234();
        int[] nums = {1,2,3,3,2,1};
        System.out.println(p.isPalindrome(ListNodeUtil.convert(nums)));
        int[] num1 = {1,2,1,2,1};
        System.out.println(p.isPalindrome(ListNodeUtil.convert(num1)));
        int[] num2 = {1,2,3,1};
        System.out.println(p.isPalindrome(ListNodeUtil.convert(num2)));

        System.out.println(p.isPalindromeTest(ListNodeUtil.convert(nums)));
        System.out.println(p.isPalindromeTest(ListNodeUtil.convert(num1)));
        System.out.println(p.isPalindromeTest(ListNodeUtil.convert(num2)));
    }
}
