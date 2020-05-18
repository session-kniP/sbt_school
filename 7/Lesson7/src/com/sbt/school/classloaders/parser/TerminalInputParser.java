package com.sbt.school.classloaders.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TerminalInputParser {

    private HashMap<String, String> flagNameMap;
    private List<TerminalParameterGroup> output;
    private int currentPos;

    public TerminalInputParser(HashMap<String, String> flagNameMap) {
        this.flagNameMap = flagNameMap;
        this.output = new ArrayList<>();
    }

    public List<TerminalParameterGroup> parse(String[] input) {
        this.output = new ArrayList<>();
        String currentFlag = null;

        int inputLength = input.length;
        currentPos = 0;

        TerminalParameterGroup group = null;

        while (currentPos < inputLength) {
            String nextParam = get(input);
            if (nextParam.startsWith("-")) {
                if (currentFlag == null) {
                    currentFlag = nextParam.substring(1);
                    String currentName = flagNameMap.get(currentFlag);

                    if (currentName == null) {
                        throw new IllegalArgumentException(String.format("%s doesn't match any flags", nextParam));
                    }

                    group = new TerminalParameterGroup(currentName, currentFlag);
                } else {
                    output.add(group);

                    String currParam = nextParam.substring(1);

                    if (currParam.equals(currentFlag)) {
                        throw new IllegalArgumentException(String.format("Unexpected parameter %s at position %d", nextParam, currentPos));
                    }

                    currentFlag = currParam;
                    String currentName;

                    if ((currentName = flagNameMap.get(currentFlag)) != null) {
                        group = new TerminalParameterGroup(currentName, currentFlag);
                    } else {
                        throw new IllegalArgumentException("Unknown flag " + currentFlag);
                    }
                }
            } else {
                if (group == null) {
                    group = new TerminalParameterGroup("", "");
                }

                group.add(new TerminalParameter(nextParam));
            }

            currentPos++;
        }

        output.add(group);

        return output;
    }


    private String get(String[] input) {
        return input[currentPos];
    }


}
