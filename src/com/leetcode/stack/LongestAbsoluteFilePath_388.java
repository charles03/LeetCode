package com.leetcode.stack;

import java.util.Stack;

/**
 * Created by charles on 3/14/17.
 * Suppose we abstract our file system by a string in the following manner:

 The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

 dir
 subdir1
 subdir2
 file.ext
 The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

 The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:

 dir
 subdir1
 file1.ext
 subsubdir1
 subdir2
 subsubdir2
 file2.ext
 The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.

 We are interested in finding the longest (number of characters) absolute path to a file within our file system. For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).

 Given a string representing the file system in the above format, return the length of the longest absolute path to file in the abstracted file system. If there is no file in the system, return 0.

 Note:
 The name of a file contains at least a . and an extension.
 The name of a directory or sub-directory will not contain a ..
 Time complexity required: O(n) where n is the size of the input string.

 Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.

 */
public class LongestAbsoluteFilePath_388 {
    /**
     * The depth of the directory/file is calculated by counting how many "\t"s are there.
     The time complexity is O(n) because each substring in the input string only goes into the stack once, and pops out from the stack once.
     */
    public int lengthLongestPath(String input) {
        String[] tokens = input.split("\n");
        Stack<Integer> stack = new Stack<>();
        int parentLen = 0; // absolute path of parent directory
        int max = 0; // as result
        int level = 0;
        int currSegmentLen = 0;
        int currWholeLen = 0;
        for (String s : tokens) {
            level = s.lastIndexOf("\t") + 1; // number of "\t"
            // if current level is parent directory of previous
            while (level < stack.size()) {
                parentLen -= stack.pop();
            }
            currSegmentLen = s.length() - level + 1; // length of current file or dir
            currWholeLen = parentLen + currSegmentLen; // after remove "\t" add "/"

            parentLen += currSegmentLen; // move to next, to set absolute length of current file or directory
            stack.push(currSegmentLen);

            if (s.contains(".")) {
                max = Math.max(max, currWholeLen - 1); // minus one is to exclude last "/" at the tail
            }
        }
        return max;
    }

    /**
     * DP solution
     */
    public int lengthLongestPathII(String input) {
        int maxLen = 0;
        String[] tokens = input.split("\n");
        int[] dp = new int[tokens.length]; // length at each level

        for (int i = 0; i < tokens.length; i++) {
            String s = tokens[i];
            int level = s.lastIndexOf("\t") + 1;

            dp[level] = s.length() - level + 1;
            if (level - 1 >= 0) {
                dp[level] += dp[level - 1];
            }
            if (s.contains(".")) {
                maxLen = Math.max(maxLen, dp[level] - 1);
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LongestAbsoluteFilePath_388 l = new LongestAbsoluteFilePath_388();
        String s1 = "dir\\n\\tsubdir1\\n\\t\\tfile1.ext\\n\\t\\tsubsubdir1\\n\\tsubdir2\\n\\t\\tsubsubdir2\\n\\t\\t\\tfile2.ext";
        System.out.println(l.lengthLongestPath(s1));
        System.out.println(l.lengthLongestPathII(s1));

    }
}
