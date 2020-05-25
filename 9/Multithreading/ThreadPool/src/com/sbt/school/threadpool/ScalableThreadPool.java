package com.sbt.school.threadpool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ScalableThreadPool implements CThreadPool {

    private final int min;
    private final int max;
    private final List<Thread> threads;
    private final Queue<Runnable> taskQueue;
    private Thread poolThread;


    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
        this.threads = new ArrayList<>(min);
        this.taskQueue = new ArrayDeque<>();
        createThreads();
    }


    private void createThreads() {
        for (int i = 0; i < min; i++) {
            threads.add(new Thread());
        }
    }


    @Override
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
    public void start() {
        this.poolThread = new Thread(this);
        this.poolThread.start();
    }


    @Override
    public synchronized void execute(Runnable runnable) {
        this.taskQueue.add(runnable);
        notify();
    }


    @Override
    public synchronized void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            deleteExcess();

            if (taskQueue.peek() == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            Thread t = getFreeThread();
            if (t != null) {

                threads.remove(t);
                t = new Thread(taskQueue.poll());
                threads.add(t);
                t.start();

            } else {
                if (threads.size() != max) {
                    t = new Thread(taskQueue.poll());
                    threads.add(t);
                    System.out.println("One more thread created. Threads left " + threads.size());
                    t.start();
                }
            }
        }

    }


    private Thread getFreeThread() {
        for (Thread thread : threads) {
            Thread.State state = thread.getState();

            if (state.equals(Thread.State.NEW) || state.equals(Thread.State.TERMINATED)) {
                return thread;
            }
        }
        return null;
    }


    private void deleteExcess() {
        List<Thread> onDelete = new ArrayList<>();

        for (Thread t : threads) {
            Thread.State state = t.getState();

            if (threads.size() - onDelete.size() > min) {
                if (state.equals(Thread.State.NEW) || state.equals(Thread.State.TERMINATED)) {
                    onDelete.add(t);
                    System.out.println("Deleted " + t.getName());
                }
            } else {
                break;
            }
        }

        threads.removeAll(onDelete);
    }

}
