package com.sbt.school.classloaders.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TerminalParameterGroup {

    private final String name;
    private final String flag;
    private List<TerminalParameter> parameters;

    public TerminalParameterGroup(String name, String flag) {
        this.name = name;
        this.flag = flag;
        this.parameters = new ArrayList<>();
    }

    public TerminalParameterGroup(String name, String flag, List<TerminalParameter> parameters) {
        this.name = name;
        this.flag = flag;
        this.parameters = parameters;
    }

    public void add(TerminalParameter parameter) {
        this.parameters.add(parameter);
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public List<TerminalParameter> getParameters() {
        return parameters;
    }

    public List<String> getStringParameters() {
        return parameters.stream().map(TerminalParameter::getValue).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Parameter Group " + name;
    }


}
