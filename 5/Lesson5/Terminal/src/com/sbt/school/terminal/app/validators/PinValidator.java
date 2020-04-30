package com.sbt.school.terminal.app.validators;

import com.sbt.school.terminal.exceptions.PinFormatException;

public class PinValidator implements Validator {

    public boolean validate(String pin) throws PinFormatException {

        //check for pin length (4)
        if (pin.length() != 4) {
            throw new PinFormatException("Length of pin should be 4");
        }

        //check for pin format(0000 - 9999)
        char[] pinChars = pin.toCharArray();

        for (char c : pinChars) {
            if (!Character.isDigit(c)) {
                throw new PinFormatException("Pin should only contain the digits");
            }
        }


        return true;
    }


}
