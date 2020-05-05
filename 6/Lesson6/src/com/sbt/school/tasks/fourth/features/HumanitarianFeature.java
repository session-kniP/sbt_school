package com.sbt.school.tasks.fourth.features;

public class HumanitarianFeature extends Feature {

    private static final String HUMANITARIAN_FEATURE_PREFIX = "HUMANITARIAN";
    private String humanitarianString;


    public HumanitarianFeature(int featureID, String featureName, String humanitarianString) {
        super(featureID, featureName);
        this.humanitarianString = humanitarianString;
    }


    public String getHumanitarianString() {
        return humanitarianString;
    }

    public void setHumanitarianString(String humanitarianString) {
        this.humanitarianString = humanitarianString;
    }

    @Override
    public String toString() {
        return HUMANITARIAN_FEATURE_PREFIX + " " + super.toString() + " & tech string is " + humanitarianString;
    }

}
