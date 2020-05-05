package com.sbt.school.tasks.fourth;

import com.sbt.school.tasks.fourth.features.Feature;
import com.sbt.school.tasks.fourth.features.HumanitarianFeature;
import com.sbt.school.tasks.fourth.features.TechnicalFeature;

import java.lang.reflect.InvocationTargetException;

public class FourthTask {



    public void gettersToSetters() {

        Feature technicalFeature = new TechnicalFeature(1, "Some tech feat", "Some TECH str");
        HumanitarianFeature humanitarianFeature = new HumanitarianFeature(3, "Some human feat", "Some HUMAN str");

        System.out.println("Before");
        System.out.println("========technicalFeature========");
        System.out.println(technicalFeature.toString());
        System.out.println("========humanitarianFeature========");
        System.out.println(humanitarianFeature.toString());
        System.out.println();

        try {
            BeanUtils.assign(technicalFeature, humanitarianFeature);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }


        System.out.println("After");
        System.out.println("========technicalFeature========");
        System.out.println(technicalFeature.toString());
        System.out.println("========humanitarianFeature========");
        System.out.println(humanitarianFeature.toString());



    }

}
