package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by charles on 12/15/16.
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

 Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):

 Any live cell with fewer than two live neighbors dies, as if caused by under-population.
 Any live cell with two or three live neighbors lives on to the next generation.
 Any live cell with more than three live neighbors dies, as if by over-population..
 Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 Write a function to compute the next state (after one update) of the board given its current state.

 Follow up:
 Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
 In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?
 */
public class GameOfLife_289 {
    /**
     * Thought:
     * use 2 bits of number to store 2 states:
     * [2nd bit, 1st bit] = [next state, current state]

     - 00  dead (next) <- dead (current)
     - 01  dead (next) <- live (current)
     - 10  live (next) <- dead (current)
     - 11  live (next) <- live (current)

     In the beginning, every cell is either 00 or 01.
     Notice that 1st state is independent of 2nd state.
     Imagine all cells are instantly changing from the 1st to the 2nd state, at the same time.
     Let's count # of neighbors from 1st state and set 2nd state bit.
     Since every 2nd state is by default dead, no need to consider transition 01 -> 00.
     In the end, delete every cell's 1st state by doing >> 1.
     For each cell's 1st bit, check the 8 pixels around itself, and set the cell's 2nd bit.

     Transition 01 -> 11: when board == 1 and lives >= 2 && lives <= 3.
     Transition 00 -> 10: when board == 0 and lives == 3.

     To get the current state, simply do
     board[i][j] & 1

     To get the next state, simply do
     board[i][j] >> 1
     */
    /*
        Besides, define direction x array and y array for iteration
     */
    private int row;
    private int col;
    // 8 directions, left, right, up, down and four diagonal
    private int[] dx = {1, 0, -1, 0, 1, -1, 1, -1};
    private int[] dy = {0, 1, 0, -1, 1, -1, -1, 1};

    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        row = board.length;
        col = board[0].length;

        int livesCount = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // invoke livedNeighbors method to check 8 directions
                livesCount = livedNeighbors(board, i, j);
                if (board[i][j] == 1 && livesCount >= 2 && livesCount <= 3) {
                    board[i][j] = 3; // transit from 01 -> 11
                }
                if (board[i][j] == 0 && livesCount == 3) {
                    board[i][j] = 2; // transit from 00 -> 10
                }
            }
        }
        // update live state all together
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] >>= 1; // update current board with next state
            }
        }
    }
    /** return the count of live cells in neighbors */
    int livedNeighbors(int[][] board, int x, int y) {
        int nx = 0;
        int ny = 0;
        int lives = 0;
        for (int i = 0; i < dx.length; i++) {
            nx = x + dx[i];
            ny = y + dy[i];
            if (0<=nx && nx<row && 0<=ny && ny<col) {
                lives += board[nx][ny] & 1;
            }
        }
        return lives;
    }

    public static void main(String[] args) {
        int[][] board = {{1, 0, 1, 0}, {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 1}};
        int[][] board1 = {{1, 0, 0, 0}, {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}};
        GameOfLife_289 g = new GameOfLife_289();
        print(board);
        g.gameOfLife(board);
        System.out.println("## AFTER ##");
        print(board);

        System.out.println("------------");
        print(board);
        g.gameOfLife(board1);
        System.out.println("## AFTER ##");
        print(board1);
    }

    private static void print(int[][] board) {
        for (int[] row : board) {
            Arrays.stream(row).forEach(t -> System.out.print(t + ","));
            System.out.println();
        }
    }
}
