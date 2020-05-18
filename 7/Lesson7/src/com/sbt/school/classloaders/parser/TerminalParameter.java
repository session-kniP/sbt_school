package com.sbt.school.classloaders.parser;

public class TerminalParameter {

    private final String value;

    public TerminalParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
