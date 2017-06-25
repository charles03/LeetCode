package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 5/23/17.
 * In the video game Fallout 4, the quest "Road to Freedom" requires players to reach a metal dial called the "Freedom Trail Ring", and use the dial to spell a specific keyword in order to open the door.

 Given a string ring, which represents the code engraved on the outer ring and another string key, which represents the keyword needs to be spelled. You need to find the minimum number of steps in order to spell all the characters in the keyword.

 Initially, the first character of the ring is aligned at 12:00 direction. You need to spell all the characters in the string key one by one by rotating the ring clockwise or anticlockwise to make each character of the string key aligned at 12:00 direction and then by pressing the center button.
 At the stage of rotating the ring to spell the key character key[i]:
 You can rotate the ring clockwise or anticlockwise one place, which counts as 1 step. The final purpose of the rotation is to align one of the string ring's characters at the 12:00 direction, where this character must equal to the character key[i].
 If the character key[i] has been aligned at the 12:00 direction, you need to press the center button to spell, which also counts as 1 step. After the pressing, you could begin to spell the next character in the key (next stage), otherwise, you've finished all the spelling.
 Example:


 Input: ring = "godding", key = "gd"
 Output: 4
 Explanation:
 For the first key character 'g', since it is already in place, we just need 1 step to spell this character.
 For the second key character 'd', we need to rotate the ring "godding" anticlockwise by two steps to make it become "ddinggo".
 Also, we need 1 more step for spelling.
 So the final output is 4.
 Note:
 Length of both ring and key will be in range 1 to 100.
 There are only lowercase letters in both strings and might be some duplcate characters in both strings.
 It's guaranteed that string key could always be spelled by rotating the string ring.

 */
public class FreedomTrail_514 {
    /**
     * DP solution
     * State : dp[i,j] is minimum steps to spell part of key start at key[j],
     *      the substring key[j..end] of key[0..j..end]
     *      when ring[i] is aligning at 12:00 position.
     *
     * why to define state like above, is because we will see dp[0,0] as final answer
     * equals min steps to spell whole key start at key[0] and ring[0] is align at 12:00
     *
     * Function:
     * for loop: idx in (all indexes in string Ring of dest char key[j])
     *      let s is index of last char match key[j-1] in ring
     *
     *      let minDistDiff = min(abs(s-idx), len() - abs(s-idx)) to cover both clockwise and anti-clock direction
     *      let remain is minimum steps to spell part of key start at j+1;
     *
     *      dp[i][j] = min(dp[i][j], minDistDiff + remain);
     *
     *      then go to next available index in Ring of dest char key[j]
     *
     * init:
     *      in order to play visiting role in DP array,
     *      fill -1 into DP initially
     * Res:
     *      dp[0][0];
     */
    public int findRotateSteps(String ring, String key) {
        int rLen = ring.length();
        int kLen = key.length();
        int[][] dp = new int[rLen][kLen];
        // init
        for (int[] line : dp) {
            Arrays.fill(line, -1);
        }
        // add key length as steps we need to push buttons
        return memorizedDp(ring, 0, key, 0, dp) + key.length();
    }
    private int memorizedDp(String ring, int i, String key, int j, int[][] dp) {
        /**
         * "i" is index of char in Ring which match with key[j-1]
         */
        if (j == key.length()) {
            return 0;
        }
        if (dp[i][j] != -1) { // already visited
            return dp[i][j];
        }
        char target = key.charAt(j); // key[j], next char need to be matched
        int idx = ring.indexOf(target);; // char index in Ring which match with key[j]

        int minStep = Integer.MAX_VALUE; // res of minimum step to spell part of key start at key[j]
        int minDist = 0; // min of distance of (last match char to next match char in string Ring)
        int minStepInRemain = 0; // min step to spell part of key start at key[j+1]
        int clock = 0, anticlock = 0; // absolute dist of two point in clockwise and anti-clock direction
        int rLen = ring.length();
        boolean firstTime = true;
        /**
         * use first time flag to achieve Do-While loop
         */
        while (idx != -1 || firstTime) {
            clock = Math.abs(i - idx);
            anticlock = rLen - clock;
            minDist = Math.min(clock, anticlock);
            /** use recursion to implement memorized dp
             * idx is currently align at 12:00 as start index of next memorized search */
            minStepInRemain = memorizedDp(ring, idx, key, j+1, dp);
            minStep = Math.min(minStep, minDist + minStepInRemain);
            /** move to next index of char matching key[j] in ring */
            idx = ring.indexOf(target, idx + 1);
            firstTime = false;
        }
        dp[i][j] = minStep;
        return minStep;
    }

    public int findRotateStepsIII(String ring, String key) {
        int r = ring.length();
        int k = key.length();
        int[][] dp = new int[k+1][r];

        int diff = 0;
        int minStep = 0;

        for (int i = k-1; i >= 0; i--) {
            for (int j = r-1; j >= 0; j--) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int m = 0; m < r; m++) {
                    if (ring.charAt(m) != key.charAt(i)) {
                        continue;
                    }
                    // loo[ each char in ring to find ring[m] == key[i]
                    diff = Math.abs(j-m); // dist to move index m to index j
                    minStep = Math.min(diff, r - diff); // find min in (closewise or anticlock) direction
                    dp[i][j] = Math.min(dp[i][j], minStep + dp[i+1][m]);
                }
            }
        }
        return dp[0][0] + key.length();
    }

    /**
     * Solution II:
     * preprocess string to clockwise and anti-clock array
     * having the steps from i-th char in Ring string to target char j in key string;
     *
     * DP def of state in solution II differ from solution I
     * State:
     * dp[i,j] is min steps to spell substr key[i,len] when ring[j] is at 12:00 pos.
     *
     * Function:
     *      assume having dp[i+1,p] which is min steps to spell substr key[i+1,len] when ring[p] is at 12:00
     *      then dp[i,j] = dp[i+1,p] + step[p->j] {which is steps of rotating from p to j clockwise}
     *      and find min comparing with the other anti-clock rotation
     *      dp[i,j] = min(dp[i,j], dp[i+1, q] + step[q->j] {which is steps of rotating from q to j anti-clockwise})
     *
     *      next thing to how to reversely deduct the value of p and q.
     *      from given input, rotating shift input as S' so let jth index of char is key[i];
     *      then index of char of current S'[0] at original input suppose to be p,
     */
    public int findRotateStepsII(String ring, String key) {
        int r = ring.length(), k = key.length();
        int[][] dp = new int[k+1][r];
        char[] chars = ring.toCharArray();
        int[][] clock = preprocess(chars, 1);
        output(clock);

        int[][] antiClock = preprocess(chars, -1);
        output(antiClock);
        int idx = 0;
        int p = 0, q = 0;
        for (int i = k-1; i >= 0; i--) {
            idx = key.charAt(i) - 'a'; // key[i]
            for (int j = r-1; j >= 0; j--) { // ring[j] is at 12:00
                p = clock[j][idx]; // ring[j] == key[i]
                q = antiClock[j][idx]; // dist in anticlock direction
                /** [..j....p..] ---clockwise move p to find j-> [...p..r+j..]
                    [..p....j..] ---clockwise move p to find j-> [...p..(r+j) % r..] */

                /** [..q....j..] ---anticlock move q to find j-> [...j..r+q..]
                    [..j....q..] ---anticlock move q to find j-> [..j..(r+q) % r..] */
                dp[i][j] = Math.min(dp[i+1][p] + (j+r - p) % r,
                        dp[i+1][q] + (q+r-j) % r);
                System.out.println(String.format("p:%s, q:%s, i:%s, j:%s, idx:%s, dp[p]:%s, dp[q]:%s, dp[j]:%s", p,q,i,j,idx, dp[i+1][p], dp[i+1][q], dp[i][j]));
            }
        }
        output(dp);
        return dp[0][0] + key.length();
    }
    private int[][] preprocess(char[] chars, int var) {
        // var is moving direction, =1 is clockwise, -1 is anticlock
        int len = chars.length;
        int[][] ans = new int[len][26];
        int[] map = new int[26];
        int idx = 0;
        for (int j = 0; j < 2 * len - 1; j++) { // expand j to double length of ring
            // so as to cover whole range in circle
            map[chars[idx] - 'a'] = idx;
            System.out.println("====  " + idx + "   ===");
            System.arraycopy(map,0,ans[idx],0,26);
//            System.out.println("index before : " + idx);
            print(map);
            System.out.println();
            idx = (idx + var + len) % len; // mod n is to get range in length of ring
//            System.out.println("index after : " + idx);
//            System.out.println("-----------");
        }
        return ans;
    }
    private void output(int[][] arr) {
        for (int[] a : arr) {
            print(a);
        }
        System.out.println("------------");
    }
    private void print(int[] a) {
        Arrays.stream(a).forEach(t->System.out.print(t + ","));
        System.out.println();
    }

    public static void main(String[] args) {
        FreedomTrail_514 f = new FreedomTrail_514();
        String r1 = "goddinga";
        String k1 = "gg";
        f.findRotateStepsII(r1, k1);
    }
}
