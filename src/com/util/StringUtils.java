package com.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by charles on 6/4/17.
 */
public class StringUtils {
    /**
     * deserialize String into List<List<?>>
     */
    public static List<List<Integer>> parseStrToIntList(String str) {
        List<List<Integer>> res = new ArrayList<>();

        Matcher matcher = Pattern.compile("\\[([^\\]]+)").matcher(str);
        int pos = -1;
        while (matcher.find(pos + 1)) {
            pos = matcher.start();
            String[] nums = matcher.group(1).split(",");
            res.add(Arrays.stream(nums).map(Integer::parseInt).collect(Collectors.toList()));
        }
        return res;
    }

    public static void main(String[] args) {
        String s1 = "[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]";
        List<List<Integer>> r1 = StringUtils.parseStrToIntList(s1);
        System.out.println(r1);
    }
}
