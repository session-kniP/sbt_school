package com.sbt.school.tests;

import com.sbt.school.collections.HashCountMap;
import com.sbt.school.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class Test {

    public static void test() {
        HashCountMap<String> strings = new HashCountMap<>();

        strings.add("Gad");
        strings.add("Gad");
        strings.add("java");
        strings.add("Java");
        strings.add("Will be 4 times");
        strings.add("Will be 4 times");
        strings.add("Will be 4 times");
        strings.add("Will be 4 times");

        System.out.println(strings.getCount("Gad"));
        System.out.println(strings.getCount("Will be 4 times"));
        System.out.println(strings.size());

        Map<String, Integer> map = strings.toMap();

        map.forEach((k, v) -> {
            System.out.println(k + " | " + v);
        });


        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(4);
        intList.add(2);
        intList.add(6);
        intList.add(5);
        intList.add(5);
        intList.add(2);


        System.out.println("=========addAll() test=========");
        List<Integer> secondIntList = new ArrayList<>();
        secondIntList.add(9);
        secondIntList.add(8);
        secondIntList.add(7);

        System.out.print("First list:\t");
        intList.forEach((i) -> System.out.print(i + ", "));

        System.out.println();

        System.out.print("Second list:\t");
        secondIntList.forEach((e) -> System.out.print(e + ", "));

        System.out.println();

        CollectionUtils.addAll(intList, secondIntList);

        System.out.print("Result list:\t");
        secondIntList.forEach((i) -> System.out.print(i + ", "));

        System.out.println();

        System.out.println("=======indexof() test==========");

        System.out.println(CollectionUtils.indexOf(secondIntList, 4));

        System.out.println("========limit() test============");
        List<Number> numList = CollectionUtils.limit(secondIntList, 6);

        numList.forEach((e) -> System.out.print(e + ", "));
        System.out.println();



        List<Integer> newList = CollectionUtils.range(intList, 2, 5, (o11, o21) ->
                o11>o21?1:o11<o21?-1:0);


        System.out.println("===========================");


        List<String> strList = new ArrayList<>();
        strList.add("abba");
        strList.add("dabba");
        strList.add("dabba");
        strList.add("laba");
        strList.add("zeko");
        strList.add("xeno-canto");
        strList.add("domra");
        strList.add("wife");
        strList.add("honey");


        //Тестовый компаратор. Если *первый символ первой строки* (1) имеет значение из
        //Юникод-таблицы большее, чем первый символ второй строки, то возвращается 1 (первая строка больше),
        //если (1) меньше, то возвращается -1 (первая строка меньше)
        //если символы равны, то возвращается 0
        List<String> newStringList = CollectionUtils.range(strList, "abba", "honey", (o11, o21) ->
        {
            char[] c11 = o11.toCharArray();
            char[] c21 = o21.toCharArray();

            int i11 = Character.getNumericValue(c11[0]);
            int i21 = Character.getNumericValue(c21[0]);

            return i11>i21?1:i11<i21?-1:0;
        });

        newStringList.forEach(System.out::println);

    }


}
