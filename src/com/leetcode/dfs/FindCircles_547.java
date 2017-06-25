package com.leetcode.dfs;

/**
 * Created by charles on 6/9/17.
 * There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.

 Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.

 Example 1:
 Input:
 [[1,1,0],
 [1,1,0],
 [0,0,1]]
 Output: 2
 Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 The 2nd student himself is in a friend circle. So return 2.
 Example 2:
 Input:
 [[1,1,0],
 [1,1,1],
 [0,1,1]]
 Output: 1
 Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends,
 so the 0th and 2nd students are indirect friends. All of them are in the same fr
 Note :
 N is in range [1,200].
 M[i][i] = 1 for all students.
 If M[i][j] = 1, then M[j][i] = 1.
 */
public class FindCircles_547 {
    /**
     * Matrix is N * N square matrix
     * each person init having himself own circle
     * so matrix(i,i) is always 1;
     * then dfs matrix, start from first person
     * if (first person connect to any other person p[j]) {
     *     dfs(current p[j[)
     * }
     * |1,0,1,0|
     * |0,1,0,1|
     * |1,0,1,1|
     * |0,1,1,1|
     */
    public int findCircleNum(int[][] M) {
        int len = M.length;
        if (len == 0) {
            return 0;
        }
        boolean[] visited = new boolean[len];
        int count = 0;
        for (int person = 0; person < len; person++) {
            if (!visited[person]) {
                dfs(M, visited, person);
                count++;
            }
        }
        return count;
    }
    private void dfs(int[][] M, boolean[] visited, int person) {
        for (int friend = 0; friend < M.length; friend++) {
            if (M[person][friend] == 1 && !visited[friend]) {
                visited[friend] = true;
                dfs(M, visited, friend);
            }
        }
    }

    public static void main(String[] args) {
        FindCircles_547 f = new FindCircles_547();
        int[][] m1 = {
                {1,0,1,0},
                {0,1,0,1},
                {1,0,1,1},
                {0,1,1,1}};
        System.out.println(f.findCircleNum(m1));
    }
}
