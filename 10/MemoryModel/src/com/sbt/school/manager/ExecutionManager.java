package com.sbt.school.manager;

import com.sbt.school.task.Task;
import com.sbt.school.threadpool.FixedThreadPool;

public interface ExecutionManager<T> {

    Context execute(Runnable callback, RunnableTask<T>... tasks) throws InterruptedException;

}
