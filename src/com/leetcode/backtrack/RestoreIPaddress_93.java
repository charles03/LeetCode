package com.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 1/22/17.
 * Given a string containing only digits, restore it by returning all possible valid IP address combinations.

 For example:
 Given "25525511135",
 return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
 */
public class RestoreIPaddress_93 {

    public List<String> restoreIpAddressesbyDFS(String s) {
        List<String> res = new ArrayList<>();
        helper(res, s, "", 0, 0);
        return res;
    }

    private void helper(List<String> res, String ip, String restored, int start, int count) {
        if (count > 4) {
            return;
        }
        if (count == 4 && start == ip.length()) {
            res.add(restored);
        }

        for (int i = 1; i < 4; i++) {
            if (start + i > ip.length()) {
                break;
            }
            String code = ip.substring(start, start + i);
            // exclude invalid case
            if ((code.startsWith("0") && code.length() > 1)
                    || (i==3 && Integer.parseInt(code) >= 256)) {
                continue;
            }
            // back track recursive traverse
            helper(res, ip, restored + code + (count == 3 ? "" : "."), start + i, count + 1);
        }
    }

    public List<String> restoreIpAddressbyJiuzhang(String s) {
        List<String> res = new ArrayList<>(); // final results contains all restored ips
        List<String> codes = new ArrayList<>(); // single possible solution for ip

        if (s.length() < 4 || s.length() > 12) {
            return res;
        }

        dfsHelper(res, codes, s, 0);
        return res;
    }

    private void dfsHelper(List<String> res, List<String> codes, String s, int start) {
        // when find valid four codes of IP
        if (codes.size() == 4) {
            if (start != s.length()) {
                return ;
            }
            StringBuilder sb = new StringBuilder();
            for (String code : codes) {
                sb.append(code).append(".");
            }
            // to remove last "."
            sb.deleteCharAt(sb.length() - 1);
            res.add(sb.toString());
        }

        // traverse
        for (int i = start; i < s.length() && i < start + 3; i++) {
            String code = s.substring(start, i + 1);
            if (isValidIpCode(code)) {
                codes.add(code);
                dfsHelper(res, codes, s, i + 1);
                codes.remove(codes.size() - 1);
            }
        }
    }

    private boolean isValidIpCode(String s) {
        if (s.charAt(0) == '0') {
            return s.equals("0"); // to eliminate cases like "00", "10"
        }
        int digit = Integer.valueOf(s);
        return digit >= 0 && digit < 256;
    }

    public static void main(String[] args) {
        RestoreIPaddress_93 r = new RestoreIPaddress_93();
        output(r.restoreIpAddressbyJiuzhang("252511135"));
        output(r.restoreIpAddressesbyDFS("252511135"));
    }

    private static void output(List<String> s) {
        s.stream().forEach(t -> System.out.print(t + ", "));
        System.out.println();
    }
}
