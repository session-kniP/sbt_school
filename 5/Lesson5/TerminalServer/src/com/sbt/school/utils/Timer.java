package com.sbt.school.utils;

public class Timer {

    private long startTime;
    private boolean started;

    public Timer() {
        this.started = false;
    }

    public void start() throws RuntimeException {
        if(started) {
            throw new RuntimeException("Timer already started");
        }
        this.started = true;
        this.startTime = System.nanoTime();
    }

    public void stop() throws RuntimeException {
        if(!started) {
            throw new RuntimeException("Timer already stopped");
        }
        this.started = false;
    }

    public long elapsed() {
        return System.nanoTime() - startTime;
    }

    public long elapsedSeconds() {
        return elapsed() / 1_000_000_000L;
    }

    public boolean isStarted() {
        return started;
    }

}
