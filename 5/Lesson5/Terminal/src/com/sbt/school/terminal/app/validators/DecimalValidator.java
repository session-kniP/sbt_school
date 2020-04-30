package com.sbt.school.terminal.app.validators;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalValidator implements Validator {

    @Override
    public boolean validate(String s) throws NumberFormatException {

        try{
            double val = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("You should enter a numeric value");
        }

        return true;
    }
}
