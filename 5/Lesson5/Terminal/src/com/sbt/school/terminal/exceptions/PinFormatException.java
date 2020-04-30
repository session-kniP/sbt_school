package com.sbt.school.terminal.exceptions;

public class PinFormatException extends RuntimeException {

    public PinFormatException(String message) {
        super(message);
    }

    public PinFormatException(String message, RuntimeException e) {
        super(message, e);
    }

}
