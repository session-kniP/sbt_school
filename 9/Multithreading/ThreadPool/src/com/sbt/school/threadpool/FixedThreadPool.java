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


    @Override
    public void start() {
        this.poolThread = new Thread(this);
        this.poolThread.start();
    }

    @Override
    public void execute(Runnable runnable) {
        taskQueue.add(runnable);
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < tasksNumber; i++) {
                Thread.State state = threads[i].getState();
                if (state.equals(Thread.State.NEW) || state.equals(Thread.State.TERMINATED)) {
                    Runnable task;
                    while ((task = taskQueue.poll()) == null) {
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    threads[i] = new Thread(task);
                    threads[i].start();
                }
            }

        }
    }
}
