package com.leetcode.linkedlist;

/**
 * Created by charles on 12/21/16.
 * You are given two linked lists representing two non-negative numbers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.

 Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 Output: 7 -> 0 -> 8
 */
public class AddTwoNumbers_2 {

    public ListNode addTwoNumber(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        ListNode dummy = new ListNode(0); // dummy node
        ListNode node = dummy;

        int carry = 0;
        int sum = 0;

        while (l1 != null && l2 != null) {
            sum = carry + l1.getVal() + l2.getVal();
            node.next = new ListNode(sum % 10);

            carry = sum / 10;
            l1 = l1.next;
            l2 = l2.next;
            node = node.next;
        }
        while (l1 != null) {
            sum = carry + l1.getVal();
            node.next = new ListNode(sum % 10);
            carry = sum / 10;
            l1 = l1.next;
            node = node.next;
        }
        while (l2 != null) {
            sum = carry + l2.getVal();
            node.next = new ListNode(sum % 10);
            carry = sum / 10;
            l2 = l2.next;
            node = node.next;
        }
        if (carry != 0) {
            node.next = new ListNode(carry);
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        AddTwoNumbers_2 a = new AddTwoNumbers_2();
        int[] num1 = {2, 4, 3};
        int[] num2 = {5, 6, 4, 1};
        ListNode l1 = ListNodeUtil.convert(num1);
        ListNode l2 = ListNodeUtil.convert(num2);
        ListNode l3 = a.addTwoNumber(l1, l2);

        ListNodeUtil.print(l1);
        ListNodeUtil.print(l2);
        ListNodeUtil.print(l3);
    }

}
