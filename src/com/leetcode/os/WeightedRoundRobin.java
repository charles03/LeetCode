package com.leetcode.os;

import com.leetcode.math.GreatestCommonDevisor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by charles on 6/24/17.
 *
 * ref :
 * https://www.oschina.net/code/snippet_593721_27586
 * http://www.cnblogs.com/xrq730/p/5154340.html
 * http://blog.csdn.net/lizhitao/article/details/25412567
 * http://blog.sina.com.cn/s/blog_68158ebf0100w3kh.html
 */
public class WeightedRoundRobin {
    private Map<String, Integer> weightMap; // key is server ip, value is weight
    private List<String> serverList; // server ip list;
    private int[] weights; // all unique weights in the given weight server map;
    private int cw = 0; // current weight
    private int pos = -1; // index of last server, init with -1
    private int gcd; // current greatest common devisor for all weights
    private int max; // max weight in whole server map

    public WeightedRoundRobin(Map<String, Integer> map) {
        init(map);
        this.weights = getWeights(weightMap);
        this.max = getMaxWeight(weights);
        this.gcd = GreatestCommonDevisor.gcd(weights);
    }
    private void init(Map<String, Integer> map) {
        this.weightMap = map;
        this.serverList = getServerList(map);
    }
    private int[] getWeights(Map<String, Integer> map) {
        Set<Integer> set = map.values().stream().collect(Collectors.toSet());
        int[] res = new int[set.size()];
        int i = 0;
        for (Integer num : set) {
            res[i++] = num;
        }
        return res;
    }
    // recreate local map to avoid concurrent issue
    private Map<String, Integer> rebuildMap() {
        Map<String, Integer> localMap = new HashMap<>();
        localMap.putAll(IP.weightMap);
        return localMap;
    }
    private List<String> getServerList(Map<String, Integer> map) {
        return new ArrayList<>(map.keySet());
    }
    // get max weight
    private int getMaxWeight(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int a : arr) {
            max = Math.max(max, a);
        }
        return max;
    }

    /** weight round robin algo to get server */
    public String getServer() {
        String server = null;
        while (true) {
            pos = (pos + 1) % serverList.size();
            if (pos == 0) {
                /** start from max weight, and descending by gcd interval */
                cw = cw - gcd; // compare current weight
                if (cw <= 0) {
                    cw = max;
                    if (cw == 0) {
                        return null;
                    }
                }
            }
            server = serverList.get(pos);
            if (weightMap.get(server) >= cw) {
                return server;
            }
        }
    }
}
