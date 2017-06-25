package com.leetcode.binarysearch;

/**
 * Created by charles on 4/12/17.
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.

 Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.

 You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.
 */
public class FirstBadVersion_278 extends VersionControl{
    /**
     * Binary search, because is sorted sequence.
     * if mid is bad, then set end to mid,
     * do not set to mid-1, because have to find exact first bad version
     */
    public int firstBadVersion(int n) {
        int start = 1;
        int end = n;
        int mid = 0;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        FirstBadVersion_278 f = new FirstBadVersion_278();
        f.createVersionControl(5,3);
        System.out.println(f.firstBadVersion(5));
        f.createVersionControl(6,3);
        System.out.println(f.firstBadVersion(6));
    }
}

class VersionControl {
    private boolean[] versions;
    public void createVersionControl(int total, int startBad) {
        versions = new boolean[total];
        if (startBad >= total) {
            return;
        }
        for (int i = startBad; i < total; i++) {
            versions[i] = true;
        }
    }

    public boolean isBadVersion(int index) {
        return versions[index];
    }
}
