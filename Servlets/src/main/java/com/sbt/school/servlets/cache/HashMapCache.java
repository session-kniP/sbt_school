package com.sbt.school.servlets.cache;

import com.sbt.school.servlets.content.Content;

import java.net.URL;
import java.util.WeakHashMap;

public class HashMapCache implements Cache<URL, Content> {

    private final WeakHashMap<URL, Content> cache;

    public HashMapCache() {
        this.cache = new WeakHashMap<>();
    }

    @Override
    public synchronized void save(URL key, Content value) {
        cache.put(key, value);
    }

    @Override
    public WeakHashMap<URL, Content> getAll() {
        return cache;
    }

    @Override
    public Content get(URL key) {
        return cache.get(key);
    }

    @Override
    public boolean contains(URL key) {
        return cache.containsKey(key);
    }

    @Override
    public synchronized void clear() {
        cache.clear();
    }

}
