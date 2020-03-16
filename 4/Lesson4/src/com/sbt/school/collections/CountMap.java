package com.sbt.school.collections;

import java.util.Map;
import java.util.Set;

public interface CountMap <T> {

    void add(T e);      //Добавляет элемент в этот контейнер

    int getCount(T e);  //Возвращает количество добавлений данного элемента

    int remove(T e);    //Удаляет элемент из контейнера и возвращает количество его добавлений(до удаления)

    int size();     //количество разных элементов

    void addAll(CountMap<T> source);      //Добавить все элементы из source в текущий контейнер, при совпадении ключей, суммировать значения

    Map<T, Integer> toMap();    //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений

    void toMap(Map<T, Integer> destination);

    Set<Map.Entry<T, Integer>> entrySet();
}
