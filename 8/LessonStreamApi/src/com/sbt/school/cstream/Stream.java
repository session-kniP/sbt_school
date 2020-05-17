package com.sbt.school.cstream;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {

    Stream<T> filter(Predicate<T> predicate);

    <N> Stream<N> transform(Function<T, N> function);

    <K, V> Map<K, V> toMap(Function<T, K> keyFunction, Function<T, V> valFunction);
}
