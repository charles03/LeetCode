package com.leetcode.string;


/**
 * Created by charles on 3/2/17.
 * Now you are given a string S, which represents a software license key which we would like to format. The string S is composed of alphanumerical characters and dashes. The dashes split the alphanumerical characters within the string into groups. (i.e. if there are M dashes, the string is split into M+1 groups). The dashes in the given string are possibly misplaced.

 We want each group of characters to be of length K (except for possibly the first group, which could be shorter, but still must contain at least one character). To satisfy this requirement, we will reinsert dashes. Additionally, all the lower case letters in the string must be converted to upper case.

 So, you are given a non-empty string S, representing a license key to format, and an integer K. And you need to return the license key formatted according to the description above.

 Example 1:
 Input: S = "2-4A0r7-4k", K = 4

 Output: "24A0-R74K"

 Explanation: The string S has been split into two parts, each part has 4 characters.
 Example 2:
 Input: S = "2-4A0r7-4k", K = 3

 Output: "24-A0R-74K"

 Explanation: The string S has been split into three parts, each part has 3 characters except the first part as it could be shorter as said above.
 Note:
 The length of string S will not exceed 12,000, and K is a positive integer.
 String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
 String S is non-empty.
 */
public class LicenseKeyFormatting_482 {
    public String licenseKeyFormatting(String str, int k) {
        StringBuilder sb = new StringBuilder();

        char[] chars = str.toCharArray();
        int cLen = chars.length;
        int idx = 0;
        for (int i = cLen - 1; i >= 0; i--) {
            if (chars[i] == '-') {
                cLen--;
                continue;
            }
            if (idx % k == 0 && idx != cLen && idx != 0) {
                sb.append("-");
            }
            sb.append(Character.toUpperCase(chars[i]));
            idx++;
        }
        return sb.reverse().toString();
    }

    public String licenseKeyFormattingII(String str, int k) {
        StringBuilder sb = new StringBuilder();
        char[] arr = str.toCharArray();
        int dashCnt = 0;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            if (arr[i] == '-') {
                dashCnt++;
            } else {
                arr[i] = Character.toUpperCase(arr[i]);
            }
        }
        // second loop to append from left to right
        int remain = (len - dashCnt) % k; // for first beginning chars
        int idx = 0;

        for (char c : arr) {
            if (c == '-') {
                continue;
            }
            if (remain > 0) {
                sb.append(c);
                remain--;
            } else {
                if (idx % k == 0 && idx > 0) {
                    sb.append('-');
                }
                idx++;
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LicenseKeyFormatting_482 l = new LicenseKeyFormatting_482();
        String s1 = "2-4A0r7-4k";
        String s2 = "a-a-a-a-";

        System.out.println(l.licenseKeyFormatting(s1, 4).equals(l.licenseKeyFormattingII(s1, 4)));

        System.out.println(l.licenseKeyFormatting(s2, 1).equals(l.licenseKeyFormatting(s2, 1)));
    }
}
