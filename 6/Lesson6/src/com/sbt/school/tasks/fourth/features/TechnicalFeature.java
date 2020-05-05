package com.sbt.school.tasks.fourth.features;

public class TechnicalFeature extends Feature {

    private static final String TECHNICAL_FEATURE_PREFIX = "TECHNICAL";
    private String technicalString;


    public TechnicalFeature(int featureID, String featureName, String technicalString) {
        super(featureID, featureName);
        this.technicalString = technicalString;
    }

    public String getTechnicalString() {
        return technicalString;
    }

    public void setTechnicalString(String technicalString) {
        this.technicalString = technicalString;
    }

    @Override
    public String toString() {
        return TECHNICAL_FEATURE_PREFIX + " " + super.toString() + " & tech string is " + technicalString;
    }
}
