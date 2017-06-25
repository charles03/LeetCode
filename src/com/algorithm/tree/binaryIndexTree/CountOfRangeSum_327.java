package com.algorithm.tree.binaryIndexTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by charles on 5/14/17.
 * {@link com.leetcode.divideconquer.CountOfRangeSum_327}
 * The idea is using BIT to maintain a counter for each prefix sum
 * and update the counter in each iteration. plus a binary search for range query
 * Since cost of add/sum operations of BIT require O(lgn) complexity.
 * the worst case time complexity of this is guaranteed to be O(nlogn);
 *
 * Let sum[i] be the prefix sum of nums[..i]. Then the range-sum of [i, j] is equal to sum[j] - sum[i - 1]. We enumerate all i's. For any fixed i,
 * we need to count the number of j's satisfying lower ≤ sum[j] - sum[i - 1] ≤ upper, i.e., lower + sum[i - 1] ≤ sum[j] ≤ upper + sum[i - 1], for all i ≤ j ≤ n.
 This kind of query can be answered, in O(log n) time, by using data structures like Fenwick Tree or Segment Tree. Therefore, the total runtime is O(n log n).
 *
 */
public class CountOfRangeSum_327 {
    /**
     * link:
     * https://discuss.leetcode.com/topic/34108/summary-of-the-divide-and-conquer-based-and-binary-indexed-tree-based-solutions/5
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        List<Long> candidates = new ArrayList<>();
        candidates.add(Long.MIN_VALUE); // to make sure no number get 0-index
        candidates.add(0L);

        long[] sum = new long[nums.length + 1];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i-1] + nums[i-1];
            candidates.add(sum[i]);
            candidates.add(lower + sum[i-1] - 1); // lower bound
            candidates.add(sum[i-1] + upper); // upper bound
        }
        Collections.sort(candidates);

        int[] BIT = new int[candidates.size()];
        int index = 0;
        // build binary index tree with only elements from prefix sum array
        for (int i = 0; i < sum.length; i++) {
            index = Collections.binarySearch(candidates, sum[i]);
            modifyPostfix(BIT, index, 1);
        }
        int count = 0;
        for (int i = 0; i < sum.length; i++) {
            // get rid of visited elements by adding -1 to corresponding tree nodes
            modifyPostfix(BIT, Collections.binarySearch(candidates, sum[i]), -1);
            // add total count of valid elems with upper bound
            count += querySum(BIT, Collections.binarySearch(candidates, upper + sum[i]));
            // minus total count of valid elems with lower bound
            count -= querySum(BIT, Collections.binarySearch(candidates, sum[i] + lower - 1));
        }
        return count;
    }
    private int lowBit(int i) {
        return i & -i;
    }
    private void modifyPostfix(int[] BIT, int i, int delta) {
        while (i < BIT.length) {
            BIT[i] += delta;
            i += lowBit(i);
        }
    }
    private int querySum(int[] BIT, int i) {
        int sum = 0;
        while (i > 0) {
            sum += BIT[i];
            i -= lowBit(i);
        }
        return sum;
    }

}
