package com.sbt.school.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils<T extends Comparable> {

    /**
     * Добавляет все элементы из source в destination
     */
    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    /**
     * Создает и возвращает новый ArrayList типа T
     */
    public static <T> List<? super T> newArrayList(Class c) {
        return new ArrayList<T>();
    }


    /**
     * Возвращает индекс элемента o в списке.
     * Если элемент не найден, возвращает -1
     */
    public static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }


    /**
     * Создает и возвращает новый ArrayList типа T,
     * копируя size элементов из списка source
     */
    public static <T> List<T> limit(List<? extends T> source, int size) {
        List<T> newList = new ArrayList<>();

        if (size <= source.size()) {
            for (int i = 0; i < size; i++) {
                newList.add(source.get(i));
            }
            return newList;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * Добавляет в source элемент o
     */
    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }


    /**
     * Удаляет все вхождения элементов списка set из списка from
     */
    public static <T> void removeAll(List<? extends T> from, List<? extends T> set) {
        from.removeAll(set);
    }

    /**
     * Проверяет, входят ли все элемменты l2 в l1
     */
    public static <T> boolean containsAll(List<? extends T> l1, List<? extends T> l2) {
        return l1.containsAll(l2);
    }

    /**
     * Проверяет, входит ли по крайней мере 1 элемент из l2 в l1
     */
    public static <T> boolean containsAny(List<? extends T> l1, List<? extends T> l2) {
        for (T el : l2) {
            if (l1.contains(el)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Возвращает новый список, содержащий элементы в диапазоне [min; max] из
     * списка list в порядке возрастания
     */
    public static <T extends Comparable<T>> List range(List<? extends T> list, T min, T max) {
        List<T> newList = new ArrayList<>();

        for (T el : list) {
            if (el.compareTo(min) >= 0 && el.compareTo(max) <= 0) {
                newList.add(el);
            }
        }

        newList.sort(Comparable::compareTo);
        return newList;
    }


    /**
     * Возвращает новый список, содержащий элементы в диапазоне [min; max] из
     * списка list в порядке возрастания
     */
    public static <T extends Comparable<T>> List range(List<? extends T> list, T min, T max, Comparator<T> c) {
        List<T> newList = new ArrayList<>();

        for (T el : list) {
            if (c.compare(el, min) >= 0 && c.compare(el, max) <= 0) {
                newList.add(el);
            }
        }

        newList.sort(c);
        return newList;
    }


}
