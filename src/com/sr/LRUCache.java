package com.sr;

import java.util.ArrayDeque;
import java.util.HashMap;

class LRUCache {
    private HashMap<Integer, Integer> mCache;
    private ArrayDeque<Integer> mUseSeq;
    private int mCapacity;

    public LRUCache(int capacity) {
        mCache = new HashMap<>();
        mUseSeq = new ArrayDeque<>();
        mCapacity = capacity;
    }

    public int get(int key) {
        if (mCache.containsKey(key)) {
            Integer ret = mCache.get(key);
            mUseSeq.remove(key);
            mUseSeq.push(key);
            return ret;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        mCache.put(key, value);
        mUseSeq.remove(key);
        mUseSeq.push(key);
        if (mUseSeq.size() > mCapacity) {
            Integer lastKey = mUseSeq.removeLast();
            mCache.remove(lastKey);
        }
    }
}