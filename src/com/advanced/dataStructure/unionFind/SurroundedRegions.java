package com.advanced.dataStructure.unionFind;

import java.util.Arrays;

/**
 * Created by charles on 11/22/16.
 * Given a 2D board containing 'X' and 'O', capture all regions surrounded by 'X'.

 A region is captured by flipping all 'O''parent into 'X''parent in that surrounded region.
 Example
 X X X X
 X O O X
 X X O X
 X O X X
 After capture all regions surrounded by 'X', the board should be:

 X X X X
 X X X X
 X X X X
 X O X X

 Tag: DFS or UnionFind
 */
public class SurroundedRegions {
    /**
     * Thought: run through boundaries and perform BFS when you come across 'O'
     * switch all adjoining 'O' to another character 'Y'
     * after, simply run through matrix again and switch 'Y' back to 'O', but 'O' to 'X'
     */
    /* instance field for _union _find */
    private int[] parent; // parent[i] = parent of i
    private int[] size; // size[i] = number of sites in subtree rooted at i

    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public void surroundedRegionsBFS(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int row = board.length;
        int col = board[0].length;

        // check first and last column
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O') {
                deepCheckAll(board, i, 1);
            }
            if (board[i][col - 1] == 'O') {
                deepCheckAll(board, i, col - 2);
            }
        }
        // check first and last row
        for (int j = 0; j < col; j++) {
            if (board[0][j] == 'O') {
                deepCheckAll(board, 1, j);
            }
            if (board[row - 1][j] == 'O') {
                deepCheckAll(board, row - 2, j);
            }
        }

        // only update center exclude board
        for (int i = 1; i < row-1; i++) {
            for (int j = 1; j < col-1; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void deepCheckAll(char[][] board, int row, int col) {
        // out of edge case
        if (row<=0 || row >= board.length-1 || col<=0 || col>=board[0].length-1) {
            return;
        }
        // already checked
        if (board[row][col] == 'X' || board[row][col] == 'Y') {
            return;
        }
        if (board[row][col] == 'O') {
            board[row][col] = 'Y'; // update
        }
        // BFS search into four directions
        deepCheckAll(board, row + 1, col);
        deepCheckAll(board, row, col + 1);
        deepCheckAll(board, row - 1, col);
        deepCheckAll(board, row, col - 1);
    }

    public static void main(String[] args) {
        char[][] board = {{'X','X','X','X'}, {'X','O','O','X'}, {'X','X','O','X'}, {'X','O','X','X'}};
        SurroundedRegions s = new SurroundedRegions();
//        parent.surroundedRegionsBFS(board);
//        s._unionFind(board);
        s.unionFind(board);
        s.printResult(board);
    }

    public void printResult(char[][] board) {
        Arrays.stream(board).forEach(t -> System.out.println(t));
    }

    /**
     * Below method based on link http://likesky3.iteye.com/blog/2240270
     */
    public void _unionFind(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int _row = board.length;
        int _col = board[0].length;
        int allRootIndex = _row * _col;

        // initialize _union _find
        _initUnionFind(_row * _col);

        int nx = 0;
        int ny = 0;
        // in the center
        for (int i = 0; i < _row; i++) {
            for (int j = 0; j < _col; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
                int current = i * _col + j; // covert 2D positions into 1D index
                // _union _find four boarders
                if (i == 0 || i == _row-1 || j == 0 || j == _col-1) {
                    _union(current, allRootIndex);
                } else {
//                    for (int k = 0; k < 4; k++) {
//                        nx = i + dx[k];
//                        ny = j + dy[k];
//                        if (0<=nx && nx<_row && 0<=ny && ny<_col && board[nx][ny] == 'O') {
//                            _union(current, nx * _col + ny);
//                        }
//                    }
                    if (j + 1 < _col && board[i][j + 1] == 'O') {
                        union(current, i * _col + j + 1);
                    }
                    if (j - 1 >= 0 && board[i][j - 1] == 'O') {
                        union(current, i * _col + j - 1);
                    }
                    if (i + 1 < _row && board[i + 1][j] == 'O') {
                        union(current, (i + 1) * _col + j);
                    }
                    if (i - 1 >= 0 && board[i - 1][j] == 'O') {
                        union(current, (i - 1) * _col + j);
                    }
                }
            }
        }

        for (int i = 0; i < _row; i++) {
            for (int j = 0; j < _col; j++) {
                if (board[i][j] == 'O' && _find(i * _col + j) != allRootIndex) {
                    board[i][j] = 'X';
                }
            }
        }
    }


    // flat 2D matrix into 1D array
    private void _initUnionFind(int n) {
        parent = new int[n + 1];
        size = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        size[n] = n + 1;
    }

    private int _find(int p) {
        if (parent[p] == p) {
            return p;
        } else {
            return parent[p] = _find(parent[p]);
        }
    }

    /**
     * below implementation is weighted quick-_union,
     * almost identical to quick-_union
     * maintain extra array to count number of elements in the tree rooted at i
     *
     * Find : identical to quick-_union
     * Union: Modify quick-_union to
     *      merge smaller tree into larger tree
     *      update size array
     */
    private void _union(int p, int q) {
        int rootP = _find(p), rootQ = _find(q);
        if (rootP == rootQ) {
            return;
        }
        // make smaller expTreeNode point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
        } else {
            if (size[rootP] == size[rootQ]) {
                size[rootP]++;
            }
            parent[rootQ] = rootP;
        }
    }

    /**
     * Below union find method based on Princeton Algorithm weighted union find
     */
    public void unionFind(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int row = board.length;
        int col = board[0].length;
        int edge_root = row * col;
        int current_root = 0;
        int surround_region_root = 0;
        initWeightedQuickUnionFind(row * col);

        int nx = 0;
        int ny = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'X') { // no need union find when original cell is 'X'
                    continue;
                }
                    current_root = i * col + j;
                if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
                    union(current_root, edge_root); // union each cell in four edges
                } else {
                    for (int k = 0; k < 4; k++) {
                        nx = i + dx[k];
                        ny = j + dy[k];
                        // connected
                        if (0<=nx && nx<row && 0<=ny && ny<col && board[nx][ny] == 'O') {
                            surround_region_root = nx * col + ny;
                            union(current_root, surround_region_root);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'O' &&
                        find(i * col + j) != edge_root) {
                    board[i][j] = 'X';
                }
            }
        }
        Arrays.stream(size).forEach(System.out::print);
        System.out.println("");
    }

    public void initWeightedQuickUnionFind(int n) {
        parent = new int[n + 1];
        size = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        size[n] = n + 1;
     }

    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int root_P = find(p);
        int root_Q = find(q);
        if (root_P == root_Q) {
            return;
        }
        // make smaller size of expTreeNode point to larger one
        if (size[root_P] < size[root_Q]) {
            parent[root_P] = root_Q;
            size[root_Q] += size[root_P];
        } else {
            parent[root_Q] = root_P;
            size[root_P] += size[root_Q];
        }
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n - 1));
        }
    }


}
