package com.leetcode.os;

import java.util.*;

/**
 * Created by charles on 6/24/17.
 * http://www.cnblogs.com/xrq730/p/5154340.html
 */
public class LoadBalance {
    public static void main(String[] args) {
        LoadBalancable l = new RoundRobin();
        LoadBalancable l2 = new WeightRoundRobin();
        for (int i = 0; i < 20; i++) {
//            System.out.println("--1--" + l.getServer());
            System.out.println("-----" + l2.getServer());
        }
    }
}

interface LoadBalancable {
    String getServer();
}
abstract class LB implements LoadBalancable{
    public Map<String, Integer> getServerMap() {
        Map<String, Integer> localMap = new HashMap<>();
        localMap.putAll(IP.weightMap);
        return localMap;
    }

    public List<String> getServerList() {
        Map<String, Integer> map = getServerMap();
        List<String> list = new ArrayList<>(map.keySet());
        return list;
    }
}

class RoundRobin extends LB {
    private static Integer pos = 0;
    @Override
    public String getServer() {
        // rebuild one map in order to avoid concurrent issue while server is up or down in runtime
        List<String> servers = getServerList();
        String server = null;
        synchronized (pos) { // lock on object
            if (pos >= servers.size()) {
                pos = 0;
            }
            server = servers.get(pos);
            pos++;
        }
        return server;
    }
}

class Random_LB extends LB{
    @Override
    public String getServer() {
        List<String> servers = getServerList();
        Random random = new Random();
        int pos = random.nextInt(servers.size());
        return servers.get(pos);
    }
}

/** via Hash to get position index based on client IP */
class Hash_LB extends LB{
    @Override
    public String getServer() {
        List<String> servers = getServerList();
        // In real web app, use HttpServlet#getRomoteIp to acquire belows
        String remoteIp = "127.0.0.1";
        int hashcode = remoteIp.hashCode();
        int pos = hashcode % servers.size();
        return servers.get(pos);
    }
}

class WeightRoundRobin extends LB {
    private static Integer pos = 0;
    @Override
    public String getServer() {
        Map<String, Integer> weightMap = getServerMap();
        List<String> servers = getServerList();
        List<String> list = new ArrayList<>();
        int weight = 0;
        for (String server : servers) {
            weight = weightMap.get(server);
            for (int i = 0; i < weight; i++) {
                list.add(server);
            }
        }
//        list.stream().forEach(t->System.out.println(t));
        String server = null;
        synchronized (pos) {
            if (pos >= servers.size()) {
                pos = 0;
            }
            server = list.get(pos);
            pos++;
        }
        return server;
    }
}

class WeightRandom extends LB {
    @Override
    public String getServer() {
        Map<String, Integer> weightMap = getServerMap();
        List<String> servers = getServerList();
        int weight = 0;
        List<String> list = new ArrayList<>();
        for (String server : servers) {
            weight = weightMap.get(server);
            for (int i = 0; i < weight; i++) {
                list.add(server);
            }
        }
        Random random = new Random();
        int pos = random.nextInt(servers.size());
        return list.get(pos);
    }
}

class LeastConnections extends LB {
    @Override
    public String getServer() {

        return null;
    }
}

class IP {
    public static Map<String, Integer> weightMap = new HashMap<>();

    static {
        weightMap.put("192.168.1.100", 1);
        weightMap.put("192.168.1.101", 1);
        weightMap.put("192.168.1.109", 1);
        weightMap.put("192.168.1.110", 1);
        // 权重为3
        weightMap.put("192.168.1.105", 3);
        weightMap.put("192.168.1.104", 3);
        // 权重为2
        weightMap.put("192.168.1.107", 2);
        weightMap.put("192.168.1.108", 2);
        weightMap.put("192.168.1.103", 2);
        // 权重为4
        weightMap.put("192.168.1.102", 4);
    }
}
