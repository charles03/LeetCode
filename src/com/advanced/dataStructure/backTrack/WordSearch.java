package com.advanced.dataStructure.backTrack;

/**
 * Created by charles on 10/17/16.
 *
 * Given a 2D board and a word, find if the word exists in the grid.

 The word can be constructed from letters of sequentially adjacent cell,
 where "adjacent" cells are those horizontally or vertically neighboring.
 The same letter cell may not be used more than once.

 Given board =

 [
 "ABCE",
 "SFCS",
 "ADEE"
 ]
 word = "ABCCED", -> returns true,
 word = "SEE", -> returns true,
 word = "ABCB", -> returns false.
 */
public class WordSearch {
    // recursion
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0) {
            return false;
        }
        if (word.length() == 0) {
            return true;
        }
        return existHelper(board, word.toCharArray());
    }

    private boolean existHelper(char[][] board, char[] word) {
        boolean res = false;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word[0]) {
                    res = find(board, i, j, word, 0);
                    if (res) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean find(char[][] board, int i, int j, char[] word, int start) {
        if (start == word.length) {
            return true;
        }
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word[start]) {
            return false;
        }
        // remember to mark the cell which already check
        board[i][j] = '#';
        boolean res = find(board, i - 1, j, word, start + 1) || find(board, i, j - 1, word, start + 1)
                    || find(board, i + 1, j, word, start + 1) || find(board, i, j + 1, word, start + 1);

        // remember to reset value back to original
        board[i][j] = word[start];
        return res;
    }
}
