package com.leetcode.string;

/**
 * Created by charles on 6/11/17.
 * You are given a string representing an attendance record for a student. The record only contains the following three characters:

 'A' : Absent.
 'L' : Late.
 'P' : Present.
 A student could be rewarded if his attendance record doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).

 You need to return whether the student could be rewarded according to his attendance record.

 Example 1:
 Input: "PPALLP"
 Output: True
 Example 2:
 Input: "PPALLL"
 Output: False
 */
public class StudentAttendenceRecordI_551 {
    public boolean checkRecord(String s) {
        int countA = 0;
        int countContinuousL = 0;
        int j = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == 'A') {
                countA++;
                if (countA > 1) {
                    return false;
                }
            }
            countContinuousL = 0;
            if (s.charAt(i) == 'L') {
                j = i+1;
                while (j < len && s.charAt(j) == 'L') {
                    countContinuousL++;
                    if (countContinuousL > 1) {
                        return false;
                    }
                    j++;
                }
                i = j-1;
            }
        }
        return true;
    }

    public boolean checkRecordII(String s) {
        int countA = 0;
        int countL = 0;

        for (char c : s.toCharArray()) {
            if (c == 'A') {
                countA++;
                countL = 0;
                if (countA >= 2) {
                    return false;
                }
            } else if (c == 'L') {
                countL++;
                if (countL >= 3) {
                    return false;
                }
            } else {
                countL = 0;
            }
        }
        return true;
    }

    public boolean checkRecordIII(String s) {
        return !s.matches(".*LLL.*|.*A.*A.*");
    }

    public static void main(String[] args) {
        StudentAttendenceRecordI_551 s = new StudentAttendenceRecordI_551();
        System.out.println(s.checkRecord("PPALLP"));
    }
}
