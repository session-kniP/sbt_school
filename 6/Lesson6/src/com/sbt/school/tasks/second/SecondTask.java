package com.sbt.school.tasks.second;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Collections.emptyList;

public class SecondTask {

    public class Runtime<T extends Number> implements Callable<Double> {

        private final List<Integer> integers = emptyList();

        public List<T> numbers() {
            return emptyList();
        }

        public List<String> strings() {
            return emptyList();
        }

        @Override
        public Double call() {
            return 0d;
        }
    }


    //Получить все возможные типы-параметры в объекте этого класса.
    public void printAllParameters() {

        Type[] implementations = Runtime.class.getGenericInterfaces();

        for (Type c : implementations) {
            System.out.println(c.getTypeName());
        }

        System.out.println("--ParameterTypes for fields");
        Field[] declaredFields = Runtime.class.getDeclaredFields();

        for (Field f : declaredFields) {
            System.out.println(f.getGenericType());
        }


        //methods
        Method[] declaredMethods = Runtime.class.getDeclaredMethods();

        for (Method m : declaredMethods) {

            System.out.println(String.format("--Method %s", m.toGenericString()));

            System.out.println(m.getGenericReturnType());

        }

    }

}
