package com.sbt.school.threadpool;

public interface CThreadPool extends Runnable {

    boolean stop();

    void start();

    void execute(Runnable runnable);

}
