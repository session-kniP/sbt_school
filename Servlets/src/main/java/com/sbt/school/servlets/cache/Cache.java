package com.sbt.school.servlets.cache;

import java.util.WeakHashMap;

public interface Cache<K, V> {

    void save(K key, V value);

    WeakHashMap<K, V> getAll();

    V get(K key);

    boolean contains(K key);

    void clear();
}
