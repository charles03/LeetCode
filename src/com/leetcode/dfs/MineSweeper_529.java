package com.leetcode.dfs;

/**
 * Created by charles on 5/23/17.
 * You are given a 2D char matrix representing the game board. 'M' represents an unrevealed mine, 'E' represents an unrevealed empty square, 'B' represents a revealed blank square that has no adjacent (above, below, left, right, and all 4 diagonals) mines, digit ('1' to '8') represents how many mines are adjacent to this revealed square, and finally 'X' represents a revealed mine.

 Now given the next click position (row and column indices) among all the unrevealed squares ('M' or 'E'), return the board after revealing this position according to the following rules:

 If a mine ('M') is revealed, then the game is over - change it to 'X'.
 If an empty square ('E') with no adjacent mines is revealed, then change it to revealed blank ('B') and all of its adjacent unrevealed squares should be revealed recursively.
 If an empty square ('E') with at least one adjacent mine is revealed, then change it to a digit ('1' to '8') representing the number of adjacent mines.
 Return the board when no more squares will be revealed.
 Example 1:
 Input:

 [['E', 'E', 'E', 'E', 'E'],
 ['E', 'E', 'M', 'E', 'E'],
 ['E', 'E', 'E', 'E', 'E'],
 ['E', 'E', 'E', 'E', 'E']]

 Click : [3,0]

 Output:

 [['B', '1', 'E', '1', 'B'],
 ['B', '1', 'M', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]

 Explanation:

 Example 2:
 Input:

 [['B', '1', 'E', '1', 'B'],
 ['B', '1', 'M', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]

 Click : [1,2]

 Output:

 [['B', '1', 'E', '1', 'B'],
 ['B', '1', 'X', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]

 Explanation:

 Note:
 The range of the input matrix's height and width is [1,50].
 The click position will only be an unrevealed square ('M' or 'E'), which also means the input board contains at least one clickable square.
 The input board won't be a stage when game is over (some mines have been revealed).
 For simplicity, not mentioned rules should be ignored in this problem. For example, you don't need to reveal all the unrevealed mines when the game is over, consider any cases that you will win the game or flag any squares.
 */
public class MineSweeper_529 {
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board; // game is over
        }
        dfs(board, x, y);
        return board;
    }
    private int[] dx = {-1,0,1,-1,1,0,1,-1};
    private int[] dy = {-1,-1,-1,0,0,1,1,1};

    /**
     * updated version
     * abstract isInRange method for reuse;
     */
    private void clickEmpty(char[][] board, int x, int y, int row, int col) {
        if (!isInRange(x,y,row,col) || board[x][y] != 'E') {
            return;
        }
        char mines = countMines(board, x, y, row, col);
        int nx = 0, ny = 0;
        if (mines == '0') {
            board[x][y] = 'B';
            for (int k = 0; k < 8; k++) {
                nx = x + dx[k];
                ny = y + dy[k];
                clickEmpty(board, nx, ny, row, col);
            }
        } else {
            board[x][y] = mines;
        }
    }
    private boolean isInRange(int x, int y, int row, int col) {
        if (0<=x && x<row && 0<=y && y<col) {
            return true;
        }
        return false;
    }
    private char countMines(char[][] board, int x, int y, int row, int col) {
        int nx = 0, ny = 0;
        char mines = '0';
        for (int k = 0; k < 8; k++) {
            nx = x + dx[k];
            ny = y + dy[k];
            if (isInRange(nx, ny, row, col) && board[nx][ny] == 'M') {
                mines++;
            }
        }
        return mines;
    }

    /**
     * single for loop with dx, dy array
     * same as two for nest loop to iterate all 8 cells based on center cell.
     */
    private void dfs(char[][] board, int x, int y) {
        if (x<0 || x > board.length || y<0 || y > board[0].length
                || board[x][y] != 'E') {
            return; // termination of dfs
        }
        int cnt = getNumsOfSurroundingBombs(board, x, y);
        int nx = 0, ny = 0;
        if (cnt == 0) {
            board[x][y] = 'B';
            // then recursively reveal 8 cells surrounding of this cell.
            for (int k = 0; k < 8; k++) {
                nx = x + dx[k];
                ny = y + dy[k];
                dfs(board, nx, ny);
            }
        } else {
            board[x][y] = (char)('0' + cnt);
        }
    }
    // eight cells surrounding center cell (x,y)
    private int getNumsOfSurroundingBombs(char[][] board, int x, int y) {
        int cnt = 0;
        int nx = 0, ny = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                nx = x + i;
                ny = y + j;
                if (nx<0 || nx>=board.length || ny<0 || ny>=board[0].length) {
                    continue;
                }
                if (board[nx][ny] == 'M' || board[nx][ny] == 'X') {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
