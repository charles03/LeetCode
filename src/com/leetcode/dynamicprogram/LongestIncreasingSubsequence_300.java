package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 2/26/17.
 * Given an unsorted array of integers, find the length of longest increasing subsequence.

 For example,
 Given [10, 9, 2, 5, 3, 7, 101, 18],
 The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note that there may be more than one LIS combination, it is only necessary for you to return the length.

 Your algorithm should run in O(n2) complexity.

 Follow up: Could you improve it to O(n log n) time complexity?
 */
public class LongestIncreasingSubsequence_300 {
    /**
     * DP
     * State : dp[i] is longest increasing subsequence upto i index
     * Function: dp[i] = max(dp[j]) + 1, j in (0, i)
     *          LIS len = max(dp[i]) i in (0, n)
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int res = 1;
        for (int i = 1; i < dp.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1;
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * DP with Binary Search
     * we consider only that portion of the dp array in which we have made the updations by inserting some elements at their correct positions(which remains always sorted). Thus, only the elements upto the i^{th}i
     ​the index in the dp array can determine the position of the current element in it.
     Since, the element enters its correct position(ii) in an ascending order in the dpdp array, the subsequence formed so far in it is surely an increasing subsequence.
     Whenever this position index ii becomes equal to the length of the LIS formed so far(lenlen), it means, we need to update the lenlen as len = len + 1len=len+1.
     */
    public int lengthOfLISII(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            /**
             * Arrays.binarySearch() method returns index of the search key, if it is contained in the array, else it returns (-(insertion point) - 1).
             * The insertion point is the point at which the key would be inserted into the array: the index of the first element greater than the key,
             * or a.length if all elements in the array are less than the specified key.
             */
            int i = Arrays.binarySearch(dp, 0, len, num);
            if (i < 0) {
                i = -(i + 1);
            }
            dp[i] = num;
            if (i == len) {
                len++;
            }
        }
        return len;
    }

    /** reference http://www.hawstein.com/posts/dp-novice-to-advanced.html
     * 假设存在一个序列d[1..9] = 2 1 5 3 6 4 8 9 7，可以看出来它的LIS长度为5。
     下面一步一步试着找出它。
     我们定义一个序列B，然后令 i = 1 to 9 逐个考察这个序列。
     此外，我们用一个变量Len来记录现在最长算到多少了

     首先，把d[1]有序地放到B里，令B[1] = 2，就是说当只有1一个数字2的时候，长度为1的LIS的最小末尾是2。这时Len=1

     然后，把d[2]有序地放到B里，令B[1] = 1，就是说长度为1的LIS的最小末尾是1，d[1]=2已经没用了，很容易理解吧。这时Len=1

     接着，d[3] = 5，d[3]>B[1]，所以令B[1+1]=B[2]=d[3]=5，就是说长度为2的LIS的最小末尾是5，很容易理解吧。这时候B[1..2] = 1, 5，Len＝2

     再来，d[4] = 3，它正好加在1,5之间，放在1的位置显然不合适，因为1小于3，长度为1的LIS最小末尾应该是1，这样很容易推知，长度为2的LIS最小末尾是3，于是可以把5淘汰掉，这时候B[1..2] = 1, 3，Len = 2

     继续，d[5] = 6，它在3后面，因为B[2] = 3, 而6在3后面，于是很容易可以推知B[3] = 6, 这时B[1..3] = 1, 3, 6，还是很容易理解吧？ Len = 3 了噢。

     第6个, d[6] = 4，你看它在3和6之间，于是我们就可以把6替换掉，得到B[3] = 4。B[1..3] = 1, 3, 4， Len继续等于3

     第7个, d[7] = 8，它很大，比4大，嗯。于是B[4] = 8。Len变成4了

     第8个, d[8] = 9，得到B[5] = 9，嗯。Len继续增大，到5了。

     最后一个, d[9] = 7，它在B[3] = 4和B[4] = 8之间，所以我们知道，最新的B[4] =7，B[1..5] = 1, 3, 4, 7, 9，Len = 5。

     于是我们知道了LIS的长度为5。

     !!!!! 注意。这个1,3,4,7,9不是LIS，它只是存储的对应长度LIS的最小末尾。有了这个末尾，我们就可以一个一个地插入数据。虽然最后一个d[9] = 7更新进去对于这组数据没有什么意义，但是如果后面再出现两个数字 8 和 9，那么就可以把8更新到d[5], 9更新到d[6]，得出LIS的长度为6。
     */
    public int LIS(int[] nums) {
        int i = 0;
        int res = 1;
        int[] ends = new int[nums.length + 1];
        for (int num : nums) {
            int insertPos = binarysearch(ends, 1, res, num);
            ends[insertPos] = num;
            if (res < insertPos) { // update length longest increasing subsequence as needed
                res = insertPos;
            }
        }
        return res;
    }
    /** search first index of elem larger than key target in the range[start, end] of array */
    private int binarysearch(int[] arr, int start, int end, int key) {
        int mid;
        if (arr[end] <= key) {
            return end + 1;
        }
        while (start < end) {
            mid = start + (end - start) / 2;
            if (arr[mid] <= key) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }
}
