package com.sbt.school.classloaders.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FlagList {

    private List<Flag> flags;
    private HashMap<String, String> flagNameMap;
    private HashMap<Flag.Type, String> typeFlagMap;
    private HashMap<String, Flag.Type> nameTypeMap;

    private int lastHash;

    public FlagList() {
        this.flags = new ArrayList<>();
        update();
        this.lastHash = flags.hashCode();
    }

    public void addFlag(Flag flag) {
        this.flags.add(flag);
    }

    public HashMap<String, String> getFlagNameMap() {
        if (flags.hashCode() != lastHash) {
            update();
        }
        return flagNameMap;
    }

    public HashMap<Flag.Type, String> getTypeFlagMap() {
        if (flags.hashCode() != lastHash) {
            update();
        }
        return typeFlagMap;
    }

    public HashMap<String, Flag.Type> getNameTypeMap() {
        if(flags.hashCode() != lastHash) {
            update();
        }
        return nameTypeMap;
    }

    public List<Flag> getFlags() {
        return flags;
    }


    private void update() {
        this.flagNameMap = (HashMap<String, String>) flags.stream().collect(Collectors.toMap(Flag::getFlag, Flag::getName));
        this.typeFlagMap = (HashMap<Flag.Type, String>) flags.stream().collect(Collectors.toMap(Flag::getType, Flag::getFlag));
        this.nameTypeMap = (HashMap<String, Flag.Type>) flags.stream().collect(Collectors.toMap(Flag::getName, Flag::getType));
        lastHash = flags.hashCode();
    }

}
