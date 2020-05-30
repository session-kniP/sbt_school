package com.sbt.school.manager;

import com.sbt.school.task.Task;
import com.sbt.school.threadpool.FixedThreadPool;

public class ExecutionManagerImpl<T> implements ExecutionManager<T>, Runnable {

    private final int threadPoolSize;

    private FixedThreadPool pool;
    private ContextImpl<T> context;
    private Runnable callback;
    private RunnableTask<T>[] tasks;

    private boolean finished;

    public ExecutionManagerImpl() {
        this.threadPoolSize = 0;
        this.finished = false;
    }

    public ExecutionManagerImpl(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        this.finished = false;
    }

    @Override
    public Context execute(Runnable callback, RunnableTask<T>... tasks) {

        if (pool == null) {
            this.context = new ContextImpl(tasks);
            this.callback = callback;
            this.tasks = tasks;
            new Thread(this).start();
        }

        return context;
    }

    @Override
    public synchronized void run() {
        pool = new FixedThreadPool(threadPoolSize > 0 ? threadPoolSize : tasks.length);   //tasks.length
        pool.start();

        for (RunnableTask<T> r : tasks) {
            pool.execute(r);
        }

        //to let thread pool start it's tasks processing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (!pool.stop()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Exception while waiting");
            }
        }

        callback.run();
        this.finished = true;
    }

    public boolean isFinished() {
        return finished;
    }
}
