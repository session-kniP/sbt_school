package com.sbt.school.tasks.third;

import com.sbt.school.tasks.third.proxy.CalcValue;
import com.sbt.school.tasks.third.proxy.Calculable;
import com.sbt.school.tasks.third.proxy.CalculableCache;
import com.sbt.school.tasks.third.proxy.Calculator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ThirdTask {


    //    Вывести на консоль все методы класса, включая все родительские методы (и приватные тоже).
    public void printAllMethods(Class<?> cl) {

        Method[] methods = cl.getDeclaredMethods();
        System.out.println(String.format("--Methods of %s", cl.getName()));

        for (Method m : methods) {
            System.out.println(m.toGenericString());
        }

        Class<?> parent = cl.getSuperclass();

        if (parent != null) {
            System.out.println(String.format("--Methods of parent - %s", parent.getName()));


            methods = parent.getDeclaredMethods();

            for (Method m : methods) {
                System.out.println(m.toGenericString());
            }
        }
    }


    //    Вывести все геттеры класса.
    public void printGetters(Class<?> cl) {

        Method[] declaredMethods = cl.getDeclaredMethods();

        for (Method m : declaredMethods) {
            if (isGetter(m)) {
                System.out.println(m.toGenericString());
            }
        }
    }


    private boolean isGetter(Method m) {
        if (m != null) {

            if (m.getName().startsWith("get")) {
                return true;
            }
            return false;

        } else {
            throw new NullPointerException("Method can't be null");
        }
    }


    //    Проверить что все строковые константы имеют значения, равные их имени
//    public static final String MONDAY = "MONDAY"
    public boolean checkIfNamesAreValues(Class<?> cl) {

        Field[] fields = cl.getDeclaredFields();

        for (Field f : fields) {
            try {
                f.setAccessible(true);
                if (!f.getName().equals((String) f.get(cl))) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Access denied, probably, field was private", e);
            }
        }


        return true;
    }


//    Реализовать кэширующий прокси

    public void getCachingProxy() {

        CalcValue<BigDecimal> first = new CalcValue<>(new BigDecimal("2.0"));
        CalcValue<BigDecimal> second = new CalcValue<>(new BigDecimal("3.0"));

        Calculator calculator = new Calculator();

        Calculable proxyCalc = (Calculable) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                Calculator.class.getInterfaces(),
                new CalculableCache(calculator)
                );

        long timePoint = System.currentTimeMillis();
        System.out.println(proxyCalc.calculate(first, second).getVal());
        System.out.println(String.format("Method execution without cache time = %d mills", System.currentTimeMillis() - timePoint));


        timePoint = System.currentTimeMillis();
        System.out.println(proxyCalc.calculate(first, second).getVal());
        System.out.println(String.format("Method execution with cache time = %d mills", System.currentTimeMillis() - timePoint));



    }


}
