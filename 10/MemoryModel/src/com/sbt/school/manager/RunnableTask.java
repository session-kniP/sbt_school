package com.sbt.school.manager;

import java.util.function.Supplier;

public class RunnableTask<T> implements Runnable {

    public volatile static int tasksCounter = 0;

    private final String taskName;
    private final Supplier<T> supplier;
    private T result;
    private boolean success;
    private boolean started;
    private boolean interrupted;
    private boolean finished;

    public RunnableTask(Supplier<T> supplier) {
        this.taskName = "Task " + getCounter();
        this.supplier = supplier;
        init();
    }

    public RunnableTask(String taskName, Supplier<T> supplier) {
        this.taskName = taskName;
        this.supplier = supplier;
        init();
    }

    private void init() {
        this.success = true;
        this.started = false;
        this.interrupted = false;
        this.finished = false;
    }

    @Override
    public void run() {
        if (!interrupted) {
            started = true;
            try {
                if (result == null) {
                    this.result = supplier.get();
                }
            } catch (RuntimeException e) {
                this.success = false;
                throw new RuntimeException("DON'T WORRY!!!!!!", e);
            } finally {
                this.finished = true;
            }
        } else {
            System.out.println("Task " + taskName + " was interrupted before start");
            return;
        }
    }

    public void interrupt() {
        this.interrupted = true;
    }

    public boolean isSuccess() {
        if (result == null) {
            return false;
        }
        return success;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isInterrupted() {
        return interrupted;
    }


    public String getTaskName() {
        return taskName;
    }


    private synchronized int getCounter() {
        return ++tasksCounter;
    }
}
