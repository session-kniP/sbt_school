package com.sbt.school.threadpool;

import java.util.ArrayDeque;
import java.util.Queue;

public class FixedThreadPool implements CThreadPool {

    private final int tasksNumber;
    private final Thread[] threads;
    private final Queue<Runnable> taskQueue;
    private Thread poolThread;

    public FixedThreadPool(int tasksNumber) {
        this.tasksNumber = tasksNumber;
        this.threads = new Thread[tasksNumber];
        this.taskQueue = new ArrayDeque<>();
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
        notify();
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
            if (!Thread.currentThread().isInterrupted()) {

                for (int i = 0; i < tasksNumber; i++) {
                    Thread.State state = threads[i].getState();

                    if (state.equals(Thread.State.NEW) || state.equals(Thread.State.TERMINATED)) {
                        Runnable task;

                        if ((task = taskQueue.poll()) == null) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                if (e.getMessage() != null) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                        threads[i] = new Thread(task);
                        threads[i].start();
                    }
                }
            } else {
                return;
            }
        }
    }
}
