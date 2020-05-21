package com.sbt.school.classloaders.input;

import com.sbt.school.classloaders.parser.TerminalInputParser;
import com.sbt.school.classloaders.parser.TerminalParameter;
import com.sbt.school.classloaders.parser.TerminalParameterGroup;

import java.util.*;
import java.util.stream.Collectors;

public class InputParameters {

    private String[] input;
    private List<TerminalParameterGroup> parameterGroups;
    private FlagList flagList;

    public InputParameters(String[] input, FlagList flagList) {
        this.input = input;
        this.flagList = flagList;
        this.parameterGroups = new TerminalInputParser(flagList.getFlagNameMap()).parse(input);
    }

    public TerminalParameterGroup getGroup(Flag.Type type) {
        try {
            String flag = flagList.getTypeFlagMap().get(type);

            return parameterGroups.stream().filter(pg -> pg.getFlag().equals(flag)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void checkParameters() throws IllegalArgumentException {
        if (input.length < 4) {
            throw new IllegalArgumentException("Input should contain at least 1 classname with 1 path");
        }

        Collection<List<TerminalParameterGroup>> dividedByName = parameterGroups.stream().collect(Collectors.groupingBy(TerminalParameterGroup::getName)).values();

        for (List<TerminalParameterGroup> dbn : dividedByName) {    //fixme too many flags only on some groups
            if (dbn.size() > 1) {
                throw new IllegalArgumentException("Too many flag '" + dbn.get(0).getName() + "' arguments");
            }
        }

        HashMap<String, Flag.Type> nameTypeMap = flagList.getNameTypeMap();


        String prevFlag = null;
        for (TerminalParameterGroup group : parameterGroups) {
            if (prevFlag == null) {
                prevFlag = group.getName();
            } else {
                String currentFlag = group.getName();

                Flag.Type currentType = nameTypeMap.get(currentFlag);

                switch (nameTypeMap.get(prevFlag)) {
                    case CLASS_NAME:

                        if (currentType == Flag.Type.HELP) {
                            throw new IllegalArgumentException("Wrong parameters: help flag should be single");
                        }

                        if (currentType.equals(Flag.Type.ARGS)) {
                            throw new IllegalArgumentException(getSequenceError(currentType, Flag.Type.METHOD_NAME));
                        }

                        break;


                    case METHOD_NAME:

                        if (currentType == Flag.Type.HELP) {
                            throw new IllegalArgumentException("Wrong parameters: help flag should be single");
                        }

                        break;


                    case ARGS:

                        if (currentType.equals(Flag.Type.HELP)) {
                            throw new IllegalArgumentException("Wrong parameters: help flag should be single");
                        }

                        if (currentType.equals(Flag.Type.ARGS)) {
                            throw new IllegalArgumentException("Wrong parameters: args flag should be single for each method name");
                        }

                        break;


                    case PATH:

                        if (currentType.equals(Flag.Type.HELP)) {
                            throw new IllegalArgumentException("Wrong parameters: help flag should be single");
                        }

                        if (currentType.equals(Flag.Type.ARGS)) {
                            throw new IllegalArgumentException(getSequenceError(currentType, Flag.Type.METHOD_NAME));
                        }

                        if (currentType.equals(Flag.Type.METHOD_NAME)) {
                            throw new IllegalArgumentException(getSequenceError(currentType, Flag.Type.CLASS_NAME));
                        }

                        if (currentType.equals(Flag.Type.PATH)) {
                            throw new IllegalArgumentException(getSequenceError(currentType, Flag.Type.CLASS_NAME, Flag.Type.METHOD_NAME, Flag.Type.ARGS));
                        }

                        break;


                    case HELP:

                        if (currentType != null) {
                            throw new IllegalArgumentException("Wrong parameters: help flag should be single");
                        }

                        break;

                }
                prevFlag = currentFlag;
            }
        }

    }


    private String getSequenceError(Flag.Type shouldBe, Flag.Type after) {
        throw new IllegalArgumentException("Flag " + flagList.getTypeFlagMap().get(shouldBe) + " should be after " + flagList.getTypeFlagMap().get(after));
    }


    private String getSequenceError(Flag.Type shouldBe, Flag.Type... after) {
        throw new IllegalArgumentException("Flag " + flagList.getTypeFlagMap().get(shouldBe) + " should be after " + getTypeString(after));
    }


    private String getTypeString(Flag.Type[] types) {
        if (types.length > 0) {
            StringBuilder builder = new StringBuilder(flagList.getTypeFlagMap().get(types[0]));
            for (int i = 1; i < types.length; i++) {
                builder.append(" | ").append(flagList.getTypeFlagMap().get(types[i]));
            }
            return builder.toString();
        }
        return "";
    }


}
