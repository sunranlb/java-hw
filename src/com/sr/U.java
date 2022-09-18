package com.sr;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

    public static Object[] readInputFromFile() {
        Object[] res = null;
        try {
            FileInputStream inputStream = new FileInputStream("fi/input.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            List<Object> r = new ArrayList<>();
            String str = null;
            List<Integer> ints = new ArrayList<>();
            while ((str = bufferedReader.readLine()) != null) {
                str = str.substring(1, str.length() - 1);
                String[] split = str.split(",");
                for (String s : split) {
                    ints.add(Integer.parseInt(s));
                }
            }
            int[] rints = new int[ints.size()];
            for (int i = 0; i < rints.length; i++) {
                rints[i] = ints.get(i);
            }
            r.add(rints);

            res = new Object[r.size()];
            res[0] = r.get(0);

            inputStream.close();
            bufferedReader.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;
    }
}
