package com.sbt.school.classloaders.input;

public class Flag {

    public enum Type {
        HELP, CLASS_NAME, METHOD_NAME, ARGS, PATH
    }

    private String flag;
    private String name;
    private Type type;

    public Flag(String flag, String name, Type type) {
        this.flag = flag;
        this.name = name;
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return flag.hashCode() + name.hashCode() + type.hashCode();
    }

    @Override
    public String toString() {
        return getFlag() + " | " + getName();
    }

}
