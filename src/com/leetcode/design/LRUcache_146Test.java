package com.leetcode.design;

import com.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by charles on 6/4/17.
 * use java reflection to invoke method runtime
 *
 * Anther way is to use groovy to dynamically invoke methods.
 */
public class LRUcache_146Test {
    private String base = "com.leetcode.design";

    @Test
    public void testLRUcache() {
        String[] arr = {"LRUcache_146","put","put","get","put","get","put","get","get","get"};
        String paramStr = "[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]";
        List<String> names = Arrays.asList(arr);
        List<List<Integer>> params = StringUtils.parseStrToIntList(paramStr);
        List<String> expects = null;
        invoke(names, params, expects);
    }

    public void invoke(List<String> names, List<List<Integer>> params, List<String> expects) {
        Object lru = initClass(base + "." + names.get(0), params.get(0));

        for (int i = 1; i < params.size(); i++) {
            Object res = null;
            if (names.get(i).equals("put")) {
                res = invokemethod(lru, names.get(i), params.get(i), new Class[]{int.class, int.class});
            } else {
                res = invokemethod(lru, names.get(i), params.get(i), new Class[]{int.class});
            }
            if (res != null) {
                System.out.println(res.toString());
            }
        }
    }

    private Object initClass(String className, List<Integer> params) {
        Object obj = null;
        try {
            Class clazz = Class.forName(className);
            Class[] types = {int.class};
            Constructor constructor = clazz.getDeclaredConstructor(types);
            Object[] objs = params.toArray();
            obj = constructor.newInstance(objs);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Object invokemethod(Object obj, String methodName, List<Integer> params, Class[] paramTypes) {
        Object res = null;
        try {
            Method method = obj.getClass().getMethod(methodName, paramTypes);
            res = method.invoke(obj, params.toArray());

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return res;
    }


}