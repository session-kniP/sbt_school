package com.sbt.school.tasks.third.proxy;

public class Calculator implements Calculable {


    @Override
    public CalcValue<?> calculate(CalcValue first, CalcValue second) {
        try {
            emulateLatency();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return first.plus(second);
    }

    private void emulateLatency() throws InterruptedException {
        Thread.sleep(500);
    }
}
