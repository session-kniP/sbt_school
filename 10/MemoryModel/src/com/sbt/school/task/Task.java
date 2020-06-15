package com.sbt.school.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Task<T> extends ReentrantLock {

    private Callable<? extends T> callable;
    private T result;
    private final Object taskLock = new Object();
    private List<Thread> waitingThreads;
    private volatile boolean success;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
        this.waitingThreads = new ArrayList<>();
        this.success = true;
    }

    public T get() {
        if (result != null) {
            return result;
        }

        System.out.println(Thread.currentThread().getName() + " tried");
        this.lock();
        System.out.println(Thread.currentThread().getName() + " entered");

        try {
            if (!success) {
                throw new TaskException("Another thread catched exception with such callable condition", Thread.currentThread(), new Throwable());
            }

            if (result != null) {
                return result;
            }
            result = callable.call();

            return result;
        } catch (Exception e) {
            this.success = false;

            throw new TaskException(e.getMessage(), Thread.currentThread(), e);
        } finally {
            if (isLocked()) {
                unlock();
            }
        }
    }

    //dat proxy pattern... for state notification
    @Override
    public void lock() {

        waitingThreads.add(Thread.currentThread());
        super.lock();
        waitingThreads.remove(Thread.currentThread());
        if (isLocked()) {
            if (waitingThreads.size() > 0) {
                System.out.println("Waiting threads:");
                for (int i = 0; i < waitingThreads.size(); i++) {
                    System.out.println("--" + waitingThreads.get(i).getName());
                }
            }
        }
    }

}
