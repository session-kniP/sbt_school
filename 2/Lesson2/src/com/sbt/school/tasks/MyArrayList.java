package com.sbt.school.tasks;

public class MyArrayList<T> {

    Thread t;

    public void start() {
        t = new Thread(this::run);
        t.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();

    }

    public void run() {
        while (true) {
            System.out.println("I'm running");

            if (!t.isInterrupted()) {
                return;
            }

        }
    }
}
