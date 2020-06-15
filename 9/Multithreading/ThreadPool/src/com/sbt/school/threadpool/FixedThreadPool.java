package com.sbt.school.threadpool;

import java.io.ObjectInputStream;
import java.util.ArrayDeque;
import java.util.Queue;

public class FixedThreadPool implements CThreadPool {

    private final int tasksNumber;
    private final Thread[] threads;
    private final Queue<Runnable> taskQueue;
    private Thread poolThread;

    private volatile boolean started;   //not started thread pool execution but at least one task started

    public FixedThreadPool(int tasksNumber) {
        this.tasksNumber = tasksNumber;
        this.threads = new Thread[tasksNumber];
        this.taskQueue = new ArrayDeque<>();
        this.started = false;
        createThreads();
    }

    private void createThreads() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread();
        }
    }

    public synchronized boolean stop() {
        for (Thread t : threads) {
            if (t.getState().equals(Thread.State.RUNNABLE) || t.getState().equals(Thread.State.TIMED_WAITING)) {
                return false;
            }
        }
        poolThread.interrupt();
        notifyAll();
        return true;
    }


    @Override
    public synchronized void start() {
        this.poolThread = new Thread(this);
        this.poolThread.start();
    }

    @Override
    public synchronized void execute(Runnable runnable) {
        taskQueue.add(runnable);
        notify();
    }

    @Override
    public synchronized void run() {
        while (true) {
            if (!poolThread.isInterrupted()) {

                for (int i = 0; i < tasksNumber; i++) {
                    Thread.State state = threads[i].getState();

                    if (state.equals(Thread.State.NEW) || state.equals(Thread.State.TERMINATED)) {
                        Runnable task;

                        if ((task = taskQueue.poll()) == null) {
                            try {
                                if (!poolThread.isInterrupted()) {
                                    wait();
                                    break;
                                }
                            } catch (InterruptedException e) {
                                if (e.getMessage() != null) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                        threads[i] = new Thread(task);
                        threads[i].start();

                        if (!started) {
                            started = true;
                        }
                    }
                }
            } else {
                return;
            }
        }

    }


    /**
     * Waiting while at least one task in thread pool begins its execution
     */
    public void waitForStart() {
        while (true) {
            if (started) break;
        }
    }

    /**
     * Waiting for ending of execution of tasks started in thread pool end stops its execution
     */
    public void waitForEnd() {
        while (true) {
            if (stop()) break;
        }
    }

}
