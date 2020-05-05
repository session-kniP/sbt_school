package com.sbt.school.tasks.third.proxy;

public interface Calculable<T extends CalcValue> {

    T calculate(T first, T second);

}
