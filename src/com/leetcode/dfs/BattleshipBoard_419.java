package com.leetcode.dfs;

/**
 * Created by charles on 3/3/17.
 * Given an 2D board, count how many battleships are in it. The battleships are represented with 'X's, empty slots are represented with '.'s. You may assume the following rules:

 You receive a valid board, made of only battleships or empty slots.
 Battleships can only be placed horizontally or vertically. In other words, they can only be made of the shape 1xN (1 row, N columns) or Nx1 (N rows, 1 column), where N can be of any size.
 At least one horizontal or vertical cell separates between two battleships - there are no adjacent battleships.
 Example:
 X..X
 ...X
 ...X
 In the above board there are 2 battleships.
 Invalid Example:
 ...X
 XXXX
 ...X
 This is an invalid board that you will not receive - as battleships will always have a cell separating between them.

 Follow up:
 Could you do it in one-pass, using only O(1) extra memory and without modifying the value of the board?
 */
public class BattleshipBoard_419 {
    /**
     * DFS solution
     */
    private int[] dx = {0, 1, 0, -1};
    private int[] dy = {1, 0, -1, 0};

    public int countBattleships(char[][] board) {
        int rLen = board.length;
        if (rLen == 0) {
            return 0;
        }
        int cLen = board[0].length;
        int cnt = 0;

        for (int i = 0; i < rLen; i++) {
            for (int j = 0; j < cLen; j++) {
                if (board[i][j] == 'X') {
                    System.out.println(i + ", " + j);
                    board[i][j] = '.'; // marked as visited
                    dfsHelper(board, i, j, rLen, cLen);
                    cnt++;
                }

            }
        }
        return cnt;
    }

    private void dfsHelper(char[][] board, int x, int y, int row, int col) {
        int nx = 0, ny = 0;
        for (int k = 0; k < 4; k++) {
            nx = x + dx[k];
            ny = y + dy[k];
            while (0<=nx && nx<row && 0<=ny && ny<col && board[nx][ny] == 'X') {
                System.out.println("search " + nx + ", " + ny);
                board[nx][ny] = '.'; // mark as visited
                // always keep search in same direction (dx[k], dx[y])
                nx += dx[k]; // move to next
                ny += dy[k];
            }
        }
    }

    public static void main(String[] args) {
        BattleshipBoard_419 b = new BattleshipBoard_419();
        char[][] b1 = {{'X','.','.','X'}, {'.','.','.','X'}, {'.','.','.','X'}};

        System.out.println(b.countBattleships(b1));
    }

    /**
     * Better Solution for Followup
     * O(n) time complexity, and without modification of matrix
     */
    public int countBattleshipII(char[][] board) {
        int cnt = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                /**
                 * for current elem, from vertical-up or horizontal-left both direction
                 * if prev is not 'X' and curr is 'X'
                 * then count of ship plus one
                 */
                if (board[i][j] == 'X'
                        && (i == 0 || board[i-1][j] != 'X')
                        && (j == 0 || board[i][j-1] != 'X')) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

}
