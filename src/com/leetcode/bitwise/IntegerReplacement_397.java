package com.leetcode.bitwise;

/**
 * Created by charles on 3/11/17.
 * Given a positive integer n and you can do operations as follow:

 If n is even, replace n with n/2.
 If n is odd, you can replace n with either n + 1 or n - 1.
 What is the minimum number of replacements needed for n to become 1?

 Example 1:

 Input:
 8

 Output:
 3

 Explanation:
 8 -> 4 -> 2 -> 1
 Example 2:

 Input:
 7

 Output:
 4

 Explanation:
 7 -> 8 -> 4 -> 2 -> 1
 or
 7 -> 6 -> 3 -> 2 -> 1
 */
public class IntegerReplacement_397 {
    public int integerReplacement(int n) {
        long num = (long)n;
        long down, up;
        long bitByDown, bitByUp;
        int count = 0;

        while (num != 1) {
            count++;
            if ((num & 1) == 0) {
                num >>= 1; // when num is even
                continue;
            }
            down = num & (num-1);
            up = num & (num+1);

            bitByDown = down & (down - 1);
            bitByUp = up & (up - 1);

            System.out.println("down " + Long.toBinaryString(down));
            System.out.println("up-- " + Long.toBinaryString(up));
            System.out.println("bit+ " + Long.toBinaryString(bitByDown));
            System.out.println("bit- " + Long.toBinaryString(bitByUp));
            if (bitByDown <= bitByUp) {
                num = num - 1;
            } else {
                num = num + 1;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        IntegerReplacement_397 i = new IntegerReplacement_397();
        System.out.println(i.integerReplacement(13));
        System.out.println(i.integerReplacementII(13));
    }

    /**
     * Here is my solution with similar idea. When N is odd, only the second bit matters.
     If the bit is '1', N+1 will remove at least one '1' in N. 1011 + 1 = 1100, 1111 + 1 = 10000. However, N - 1 will remove only one '1'. 1011 - 1 = 1010 or 1111 - 1 = 1110. So we favor N + 1 here.
     If the bit is '0', N+1 will remove zero '1'. 1001 + 1 = 1010. N -1 will remove one '1'. 1001 - 1 = 1000.
     N = 3 is a special case.
     */
    public int integerReplacementII(int n) {
        long N = n;
        int count = 0;
        while (N != 1) {
            if ((N & 1) == 0) {
                N >>= 1;
            } else {
                // special cases
                if (N == 3) {
                    count += 2;
                    break;
                }
                if ((N & 2) == 2) { // when second lowest digit is one
                    N = N + 1;
                } else { // when second lowest digits is zero
                    N = N - 1;
                }
            }
            count++;
        }
        return count;
    }
}
