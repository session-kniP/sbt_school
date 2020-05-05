package com.sbt.school.tasks.first;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class FirstTask {

    private Class testingClass;

    public FirstTask(Class testingClass) {
        this.testingClass = testingClass;
    }


//    Для класса String получить и вывести на экран:

    //Все модификаторы типа
    public void printTypeModifiers() {

        Method[] methods = testingClass.getDeclaredMethods();

        for (Method m : methods) {
            System.out.println(
                    String.format("Modifier\t%s\t for field\t%s",
                            Modifier.toString(m.getModifiers()),
                            m.getName()
                    )
            );
        }


    }


    //Имя пакета
    public void printPackageName() {
        System.out.println(testingClass.getPackageName());
    }


    //Классы иерархии
    public void printHierarchyClasses() {

        System.out.println(testingClass.getSuperclass().getName());

        Class[] classes = testingClass.getDeclaredClasses();

        for (Class cl : classes) {
            System.out.println(cl.getName());
        }
    }


    //Реализуемые интерфейсы для класса и для его родителей
    public void printInterfaces() {
        Class[] interfaces = testingClass.getInterfaces();

        System.out.println(String.format("---Interfaces for %s", testingClass.getName()));
        for (Class i : interfaces) {
            System.out.println(i.getName());
        }

        System.out.println("---Interfaces for parents: ");

        Class parent = testingClass;
        while ((parent = parent.getSuperclass()) != null) {
            System.out.println(String.format("------Interfaces for %s", parent.getName()));
            for (Class parentInterface : parent.getInterfaces()) {
                System.out.println(parentInterface.getName());
            }
        }
    }



    public String getTestingClassName() {
        return testingClass.getName();
    }

}
