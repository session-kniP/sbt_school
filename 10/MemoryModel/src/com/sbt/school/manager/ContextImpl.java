package com.sbt.school.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ContextImpl<T> implements Context {

    private final RunnableTask<T>[] tasks;


    public ContextImpl(RunnableTask<T>... tasks) {
        this.tasks = tasks;
    }


    @Override
    public int getCompletedTaskCount() {
        return (int) Arrays.stream(tasks).filter(RunnableTask::isStarted).filter(RunnableTask::isSuccess).count();
    }

    @Override
    public int getFailedTaskCount() {
        return (int) Arrays.stream(tasks).filter(t -> !t.isSuccess()).filter(RunnableTask::isFinished).count();
    }

    @Override
    public void interrupt() {
        for (RunnableTask<T> task : tasks) {
            if (!task.isStarted()) {
                task.interrupt();
            }
        }
        debugInterrupted();
    }

    @Override
    public int getInterruptedTaskCount() {
        return (int) Arrays.stream(tasks).filter(RunnableTask::isInterrupted).count();
    }

    @Override
    public boolean isFinished() {
        for (RunnableTask<T> t : tasks) {
            if (!t.isStarted() || !t.isInterrupted()) {
                return false;
            }
        }
        return true;
    }


    //just for debug
    private void debugInterrupted() {
        List<RunnableTask<T>> collect = Arrays.stream(tasks).filter(RunnableTask::isInterrupted).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("Interrupted: ");
        for (RunnableTask<T> task : collect) {
            sb.append(task.getTaskName()).append(" ");
        }
        System.out.println(sb.toString());
    }

}
