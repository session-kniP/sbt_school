package com.sbt.school.utils;

public abstract class Utils {

    public static final int INCH = 3;   //1 inch == 3 centimeters
    public static final int FOOT = 12;  //1 foot == 12 inches

    public static int sum(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }


    public static int getInches(int number) {

        int result = number / INCH;
        int diff = number - result * INCH;
        if (diff >= 2) {
            result++;
        }

        return result;
    }

    public static int getFoots(int inches) {
        return inches / FOOT;
    }

}
