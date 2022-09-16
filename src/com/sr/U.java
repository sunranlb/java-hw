package com.sr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class U {
    public static List<Integer> makeList(int... a) {
        List<Integer> res = new ArrayList<>();
        for (int i : a) {
            res.add(i);
        }
        return res;
    }

    public static List<List<Integer>> makeListList(List<Integer>... a) {
        return new ArrayList<>(Arrays.asList(a));
    }

}
