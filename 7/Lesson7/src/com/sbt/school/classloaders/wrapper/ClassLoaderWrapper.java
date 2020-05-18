package com.sbt.school.classloaders.wrapper;

import com.sbt.school.classloaders.target.ModifyMain;

import javax.swing.plaf.metal.MetalTextFieldUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            if (Arrays.stream(methods).anyMatch(m -> m.getName().equals(methodName) && m.getParameterTypes().length == 0)) {
                return cl;
            }
        }

        throw new ClassNotFoundException(String.format("Class %s with method %s not found", className, methodName));
    }


    public Class<?> findClassWithMethod(String className, String methodName, List<String> paths, String... arguments) throws FileNotFoundException, ClassNotFoundException {
        List<Class<?>> classes = findClasses(paths, className);

        for (Class<?> cl : classes) {
            Class.forName("String");
            Method[] methods = cl.getDeclaredMethods();

            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    Class<?>[] types = m.getParameterTypes();

                    if (compareArgumentNames(types, arguments)) {
                        return cl;
                    }
                }
            }
        }

        String exceptionString = getArgumentExceptionString(arguments);

        throw new ClassNotFoundException(String.format(exceptionString, className, methodName, arguments));
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
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
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


    private String getArgumentExceptionString(String[] arguments) {
        StringBuilder exceptionBuilder = new StringBuilder();
        exceptionBuilder.append("Class %s with method (%s");

        for (int i = 0; i < arguments.length; i++) {
            exceptionBuilder.append(arguments[i]);
            if (i != arguments.length - 1) {
                exceptionBuilder.append("%s, ");
            }
        }

        exceptionBuilder.append(") not found");
        return exceptionBuilder.toString();
    }


    /**
     * @param methodArguments classes of arguments of methods
     * @param targetArguments classnames of target arguments
     * @return true if argument classnames of method are equal to all target arguments, false otherwise
     */
    private boolean compareArgumentNames(Class<?>[] methodArguments, String... targetArguments) {
        //a little bit high level
        List<String> argNames = Arrays.stream(methodArguments).map(Class::getName).collect(Collectors.toList());
        List<String> argNamesWithoutPackages = argNames.stream().map(n -> n.replaceAll(".+\\.", "")).collect(Collectors.toList());

        for (String arg : targetArguments) {
            if (withPackage(arg)) {
                if (!argNames.contains(arg)) {
                    return false;
                }
            } else {
                if (!argNamesWithoutPackages.contains(arg)) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Checks if the classname is with package:
     * Example: some.package.name.ClassName     - with package
     *          ClassName - without package
     * @param name classname of argument
     * @return true if class name is in with-package notation, false otherwise
     */
    private boolean withPackage(String name) {
        if (name.replaceAll(".+\\.", "").equals(name)) {
            return false;
        }
        return true;
    }

}
