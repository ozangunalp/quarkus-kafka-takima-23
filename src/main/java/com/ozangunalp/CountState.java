package com.ozangunalp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CountState {

    public Map<String, Long> countPerPath = new ConcurrentHashMap<>();

    public CountState increment(String path) {
        countPerPath.compute(path, (s, l) -> l == null ? 1L : l + 1);
        return this;
    }

    public long get(String path) {
        return countPerPath.getOrDefault(path, 0L);
    }

    @Override
    public String toString() {
        return "CountState{" +
                "countPerPath=" + countPerPath +
                '}';
    }
}