package com.sbt.school.cstream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Collection;


public class CStream<T> implements Stream<T> {

    private Collection<T> collection;

    public CStream(Collection<T> collection) {
        this.collection = collection;
    }


    public static <T> CStream<T> of(Collection<T> collection) {
        return new CStream<>(collection);
    }


    @Override
    public CStream<T> filter(Predicate<T> predicate) {
        Collection<T> newCollection = new ArrayList<>();

        collection.forEach(el -> {
            if (predicate.test(el)) {
                newCollection.add(el);
            }
        });

        return new CStream<>(newCollection);
    }


    @Override
    public <N> CStream<N> transform(Function<T, N> function) {
        Collection<N> newCollection = new ArrayList<>();

        collection.forEach(el -> newCollection.add(function.apply(el)));

        return new CStream<N>(newCollection);
    }


    @Override
    public <K, V> Map<K, V> toMap(Function<T, K> keyFunction, Function<T, V> valFunction) {
        Map<K, V> map = new HashMap<>();

        collection.forEach(el -> {
            map.put(
                    keyFunction.apply(el),
                    valFunction.apply(el)
            );
        });

        return map;
    }
}
