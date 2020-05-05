package com.sbt.school.tasks.third.proxy;

import java.math.BigDecimal;

public class CalcValue<T extends BigDecimal> {

    private T value;

    public CalcValue(T value) {
        this.value = value;
    }


    public T getVal() {
        return value;
    }

    public CalcValue<T> plus(CalcValue other) {
        return new CalcValue<>((T) value.add(other.getVal()));
    }

}
