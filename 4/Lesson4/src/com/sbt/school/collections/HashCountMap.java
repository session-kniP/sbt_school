package com.sbt.school.collections;

import java.util.*;

public class HashCountMap<T> implements CountMap<T>, Iterable {      //CountMap based on HashMap

    private Map<T, Integer> map;

    public HashCountMap() {
        this.map = new HashMap<>();
    }


    @Override
    public void add(T e) {
        Integer prevCount = map.get(e);
        if (prevCount == null) {
            map.put(e, 1);
        } else {
            prevCount++;
            map.replace(e, prevCount);
        }
    }

    @Override
    public int getCount(T e) {
        return map.get(e);
    }

    @Override
    public int remove(T e) {
        return map.remove(e);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<T> source) {
        for(Map.Entry<T, Integer> el : source.entrySet()) {
            add(el.getKey());
        }
    }

    @Override
    public Map<T, Integer> toMap() {
        return map;
    }

    @Override
    public void toMap(Map<T, Integer> destination) {
        destination = map;
    }



    @Override
    public Set<Map.Entry<T, Integer>> entrySet() {
        return map.entrySet();
    }


    //Iterable
    @Override
    public Iterator iterator() {
        return map.entrySet().iterator();
    }
}
