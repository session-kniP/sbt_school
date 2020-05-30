package com.sbt.school.manager;

public interface Context {

    int getCompletedTaskCount();

    int getFailedTaskCount();

    //declines execution of tasks that didn't began to execute yet
    void interrupt();

    int getInterruptedTaskCount();

    //checks if all of tasks are executed or interrupted
    boolean isFinished();

}
