package com.sbt.school.classloaders;


import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;

/**
 * This class calls private methods from ModifyMain.java using reflections
 */
public class ClassLoaderExecutor {

    private ModifyMain modifyMain;

    public ClassLoaderExecutor() {
        this.modifyMain = new ModifyMain();
    }

    public ClassLoaderExecutor(ModifyMain modifyMain) {
        this.modifyMain = modifyMain;
    }



    public  void checkDirectory(String dirName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Method method;

        try {
            method = ModifyMain.class.getDeclaredMethod("checkDirectory", String.class);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("Cannot find method %s in class %s", "checkDirectory", ModifyMain.class.getName()));
        }

        try {
            if (!method.canAccess(method)) {
                method.setAccessible(true);
            }

            try {
                method.invoke(modifyMain, dirName);
            } catch (IllegalAccessException e) {
                throw new IllegalAccessException(String.format("Method %s is private and cannot be accessed", method.getName()));
            }

        } catch (InvocationTargetException e) {
            throw new InvocationTargetException(e);
        }
    }


    public Class<?> loadClass(URLClassLoader classLoader, String clazzName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Method method;

        try {
            method = ModifyMain.class.getDeclaredMethod("loadClass", URLClassLoader.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("Cannot find method %s in class %s", "loadClass", ModifyMain.class.getName()));
        }

        try {
            method.setAccessible(true);

            try {
                return (Class<?>) method.invoke(null, classLoader, clazzName);
            } catch (IllegalAccessException e) {
                throw new IllegalAccessException(String.format("Method %s is private and cannot be accessed", method.getName()));
            }

        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new InvocationTargetException(e);
        }
    }


    public void generateClass(String dirName, String clazzName, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method;

        try {
            method = ModifyMain.class.getDeclaredMethod("generateClass", String.class, String.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("Cannot find method %s in class %s", "generateClass", ModifyMain.class.getName()));
        }

        try {
            if (!method.canAccess(method)) {
                method.setAccessible(true);
            }

            try {
                method.invoke(modifyMain, dirName, clazzName, methodName);
            } catch (IllegalAccessException e) {
                throw new IllegalAccessException(String.format("Method %s is private and cannot be accessed", method.getName()));
            }

        } catch (InvocationTargetException e) {
            throw new InvocationTargetException(e);
        }
    }


    public boolean searchMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method;

        try {
            method = ModifyMain.class.getDeclaredMethod("searchMethod", Class.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("Cannot find method %s in class %s", "searchMethod", ModifyMain.class.getName()));
        }

        try {
            if (!method.canAccess(method)) {
                method.setAccessible(true);
            }

            try {
                return (boolean) method.invoke(modifyMain, clazz, methodName);
            } catch (IllegalAccessException e) {
                throw new IllegalAccessException(String.format("Method %s is private and cannot be accessed", method.getName()));
            }

        } catch (InvocationTargetException e) {
            throw new InvocationTargetException(e);
        }
    }


    public URLClassLoader getClassLoaderByDir(String directoryName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method;

        try {
            method = ModifyMain.class.getDeclaredMethod("getClassLoaderByDir", String.class);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("Cannot find method %s in class %s", "getClassLoaderByDir", ModifyMain.class.getName()));
        }

        try {
            method.setAccessible(true);

            try {
                URLClassLoader classLoader = (URLClassLoader) method.invoke(modifyMain, directoryName);
                return classLoader;
            } catch (IllegalAccessException e) {
                throw new IllegalAccessException(String.format("Method %s is private and cannot be accessed", method.getName()));
            }

        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new InvocationTargetException(e.getCause());
        }
    }


}
