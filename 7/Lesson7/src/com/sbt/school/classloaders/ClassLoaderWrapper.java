package com.sbt.school.classloaders;

import javax.naming.Name;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @ Class-wrapper on ModifyMain.java
 */
public class ClassLoaderWrapper {

    private final String extensionRegex = "[.]class";
    private ClassLoaderExecutor executor;

    public enum NameType {
        WITH_PACKAGE,
        WITHOUT_PACKAGE
    }

    public ClassLoaderWrapper() {
        this.executor = new ClassLoaderExecutor();
    }

    public ClassLoaderWrapper(ClassLoaderExecutor executor) {
        this.executor = executor;
    }

    public ClassLoaderWrapper(ModifyMain modifyMain) {
        this.executor = new ClassLoaderExecutor(modifyMain);
    }


    public Class<?> findClassWithMethod(String className, String methodName, List<String> paths) throws FileNotFoundException, ClassNotFoundException {

        List<Class<?>> classes = findClasses(paths, className);


        for (Class<?> cl : classes) {
            Method[] methods = cl.getDeclaredMethods();
            if (Arrays.stream(methods).anyMatch(m -> m.getName().equals(methodName))) {
                return cl;
            }
        }

        throw new ClassNotFoundException(String.format("Class %s with method %s not found", className, methodName));
    }


    public List<Class<?>> findAllClassesWithMethod(String className, String methodName, List<String> paths) throws FileNotFoundException, ClassCastException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        List<Class<?>> classes = findClasses(paths, className);


        List<Class<?>> allClasses = new ArrayList<>();

        for (Class<?> cl : classes) {
            Method[] methods = cl.getDeclaredMethods();
            for (Method m : methods) {
                try {
                    if (executor.searchMethod(cl, m.getName())) {
                        allClasses.add(cl);
                    }
                } catch (NoSuchMethodException e) {
                    throw new NoSuchMethodException(String.format("Method %s not found in class %s", m.getName(), cl.getName()));
                } catch (IllegalAccessException e) {
                    throw new IllegalAccessException(String.format("Access denied for %s", m.getName()));
                } catch (InvocationTargetException e) {
                    throw new InvocationTargetException(e, String.format("Can't invoke %s", m.getName()));
                }
            }
        }

        return allClasses;
    }


    private List<Class<?>> findClasses(List<String> paths, String className) throws FileNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        for (String p : paths) {
            try {
                classes.addAll(getClasses(p, className));
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Exception while searching classes");
            }
        }

        return classes;
    }




    private List<Class<?>> getClasses(String dirPath, String className) throws FileNotFoundException {

        List<Class<?>> classes = new ArrayList<>();
        List<File> files;

        try {
            files = getFiles(dirPath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format("File or directory %s not found", dirPath));
        }

        URLClassLoader loader = null;  //file.getAbsoluteFile().getParent()
        try {
            loader = executor.getClassLoaderByDir(dirPath);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        for (File file : files) {
            try {
                if (file.getName().endsWith(".class") && file.getName().replaceAll(extensionRegex, "").equals(className)) {
                    Class<?> loaded = executor.loadClass(loader, className);    //file.getName().replaceAll(extensionRegex, "")
                    if (loaded != null) {
                        classes.add(loaded);
                    }
                }
            }  catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return classes;
    }


    private List<File> getFiles(String dirPath) throws FileNotFoundException {
        File filePath = new File(dirPath);
        if (!filePath.exists()) {
            throw new FileNotFoundException(String.format("Directory %s not found", dirPath));
        }


        List<File> files = new ArrayList<>();

        if (!filePath.isDirectory()) {
            throw new IllegalArgumentException(String.format("%s is not a directory path", dirPath));
        }


        for (File file : filePath.listFiles()) {
            if (file.isDirectory()) {
                List<File> inDir = getFiles(file.getPath());
                if (inDir.size() > 0) {
                    files.addAll(inDir);
                }
            } else {
                files.add(file);
            }
        }

        return files;

    }


}
