package com.sbt.school.threadpool;

public interface CThreadPool extends Runnable {

    void start();

    void execute(Runnable runnable);

}
