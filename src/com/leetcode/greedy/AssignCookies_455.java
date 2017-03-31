package com.leetcode.greedy;

import java.util.Arrays;

/**
 * Created by charles on 3/19/17.
 * Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at most one cookie. Each child i has a greed factor gi, which is the minimum size of a cookie that the child will be content with; and each cookie j has a size sj. If sj >= gi, we can assign the cookie j to the child i, and the child i will be content. Your goal is to maximize the number of your content children and output the maximum number.

 Note:
 You may assume the greed factor is always positive.
 You cannot assign more than one cookie to one child.

 Example 1:
 Input: [1,2,3], [1,1]

 Output: 1

 Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3.
 And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content.
 You need to output 1.
 Example 2:
 Input: [1,2], [1,2,3]

 Output: 2

 Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2.
 You have 3 cookies and their sizes are big enough to gratify all of the children,
 You need to output 2.
 */
public class AssignCookies_455 {
    /** use greedy solution after sorting both arrays */
    /** as for case
     * children, min cookie for content : [7,8,9,10]
     * cookie, cookies [5,6,7,8]
     * child index will increase only when cookie index move to 7 and 8
     * Thus, child final count increase to 2
     */
    public int findContentChildren(int[] children, int[] cookies) {
        Arrays.sort(children);
        Arrays.sort(cookies);

        int len1 = children.length;
        int len2 = cookies.length;
        int child = 0; // as result of count of content of child
        for (int cookie = 0; child < len1 && cookie < len2; cookie++) {
            if (cookies[cookie] >= children[child]) {
                child++;
            }
        }
        return child;
    }
}
