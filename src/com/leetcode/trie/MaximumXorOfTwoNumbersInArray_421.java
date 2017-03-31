package com.leetcode.trie;

/**
 * Created by charles on 2/20/17.
 * {@link com.leetcode.bitwise.MaximumXorOfTwoNumbersInArray_421}
 * Trie Data Structure
 * 1) create an empty Trie. Every node of Trie is going to contain two children,  for 0 & 1 value of bit
 * 2) Init pre_xor = 0 and insert into Trie
 * 3) Init result = negative infinite
 * 4) Traverse the given array and do following for every array element arr[i]
 *      a) pre_xor = pre_xor ^ arr[i] ; pre_xor now contains xor of elements from arr[0] to arr[i]
 *      b) Query the maximum xor value ending with arr[i]
 *      c) Update result if the value obtained in step 4.b is more than current value of result
 */
public class MaximumXorOfTwoNumbersInArray_421 {
    /**
     * We can observe from above algorithm that we build a Trie that contains XOR of all prefixes of given array. To find the maximum XOR subarray ending with arr[i], there may be two cases.
     i) The prefix itself has the maximum XOR value ending with arr[i]. For example if i=2 in {8, 2, 1, 12}, then the maximum subarray xor ending with arr[2] is the whole prefix.
     ii) We need to remove some prefix (ending at index from 0 to i-1). For example if i=3 in {8, 2, 1, 12}, then the maximum subarray xor ending with arr[3] starts with arr[1] and we need to remove arr[0].

     To find the prefix to be removed, we find the entry in Trie that has maximum XOR value with current prefix. If we do XOR of such previous prefix with current prefix, we get the maximum XOR value ending with arr[i].
     If there is no prefix to be removed (case i), then we return 0 (that’s why we inserted 0 in Trie).
     */
    private class TrieNode {
        int value;
        TrieNode[] next;
        TrieNode() {
            this.value = 0;
            // use two status to determine whether it should insert or
            next = new TrieNode[2];
        }
    }

    public void insert(TrieNode root, int pre_xor) {
        TrieNode node = root;
        for (int i = 31; i >= 0; i--) {
            // find current bit in given prefix
            int index = (pre_xor & (1 << i)) > 0 ? 1 : 0;
            // create new node if needed
            if (node.next[index] == null) {
                node.next[index] = new TrieNode();
            }
            node = node.next[index];
        }
        // store value at leaf node
        node.value = pre_xor;
    }

    // find maximum XOR ending with last number in prefix XOR 'pre_xor'
    // and returns the XOR of this maximum with pre_xor which is max XOR ending with last elem of pre_xor
    public int query(TrieNode root, int pre_xor) {
        TrieNode node = root;
        for (int i = 31; i >= 0; i--) {
            // find current bit in given prefix
            int index = (pre_xor & (1 << i)) > 0 ? 1 : 0;
            // traverse trie, first look for a prefix that has opposite bit
            if (node.next[1 - index] != null) {
                node = node.next[1 - index];
            } else if (node.next[index] != null) {
                // if there is no prefix with opposite bit, then look for same bit
                node = node.next[index];
            }
        }
        return pre_xor ^ node.value;
    }
    // return max XOR value of subarray in nums
    public int maxSubarrayXOR(int[] nums) {
        // create trie and insert 0 into it
        TrieNode root = new TrieNode();
        insert(root, 0);
        // init answer and xor of current prefix
        int res = Integer.MIN_VALUE;
        int pre_xor = 0;

        // Traverse all input array elem
        for (int i = 0; i < nums.length; i++) {
            // update current prefix xor and insert into Trie
            pre_xor = pre_xor ^ nums[i];
            insert(root, pre_xor);

            // Query for current prefix xor in Trie and update result if required
            res = Math.max(res, query(root, pre_xor));
        }
        return res;
    }

    public static void main(String[] args) {
        MaximumXorOfTwoNumbersInArray_421 m = new MaximumXorOfTwoNumbersInArray_421();
        int[] n1 = {8,1,2,12};
        System.out.println(m.maxSubarrayXOR(n1));
    }
}
