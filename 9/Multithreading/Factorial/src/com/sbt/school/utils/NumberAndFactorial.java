package com.sbt.school.utils;

import java.math.BigInteger;

public class NumberAndFactorial {

    private final int number;
    private final BigInteger factorial;

    public NumberAndFactorial(int number, BigInteger factorial) {
        this.number = number;
        this.factorial = factorial;
    }

    @Override
    public String toString() {
        return "For number " + number + " factorial is " + factorial;
    }
}
