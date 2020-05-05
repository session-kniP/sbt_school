package com.sbt.school;

import com.sbt.school.tasks.first.FirstTask;
import com.sbt.school.tasks.fourth.FourthTask;
import com.sbt.school.tasks.second.SecondTask;
import com.sbt.school.tasks.third.ThirdTask;
import com.sbt.school.tasks.third.NotEqualClass;

import java.lang.reflect.Field;

public class Main {

    static class Test {

        private String some;

        public Test() {
        }

        public String getSome() {
            return some;
        }

    }


    public static void main(String[] args) {
        //reflection, proxy, annotations

        System.out.println("\n-=-=-=-=Task 1-=-=-=-=\n");

        Class testingClass = String.class;

        FirstTask firstTask = new FirstTask(testingClass);
        System.out.println(String.format("=========%s modifiers========:", firstTask.getTestingClassName()));
        firstTask.printTypeModifiers();
        System.out.println("=========Package name========:");
        firstTask.printPackageName();
        System.out.println("=========Hierarchy classes========:");
        firstTask.printHierarchyClasses();
        System.out.println("=========Interfaces=============:");
        firstTask.printInterfaces();

        System.out.println("\n-=-=-=-=Task 2-=-=-=-=\n");

        SecondTask secondTask = new SecondTask();
        secondTask.printAllParameters();

        System.out.println("\n-=-=-=-=Task 3-=-=-=-=\n");
        ThirdTask thirdTask = new ThirdTask();
        System.out.println("=========All methods========:");
        thirdTask.printAllMethods(String.class);
        System.out.println("=========Getters========:");
        thirdTask.printGetters(String.class);
        System.out.println("\"=========Names are values========:\"");
        System.out.println(thirdTask.checkIfNamesAreValues(NotEqualClass.class));
        System.out.println("\"=========Caching proxy========:\"");
        thirdTask.getCachingProxy();

        System.out.println("\n-=-=-=-=Task 4-=-=-=-=\n");
        FourthTask fourthTask = new FourthTask();
        fourthTask.gettersToSetters();



    }

}
