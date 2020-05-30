package com.sbt.school.task;

public class TaskException extends RuntimeException {

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(String message, Thread caller, Throwable cause) {
        super("Exception in thread " + caller.getName() + "\n" + message, cause);
    }

}
