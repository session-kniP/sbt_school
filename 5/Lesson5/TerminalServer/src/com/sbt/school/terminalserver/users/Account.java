package com.sbt.school.terminalserver.users;

import javax.management.InvalidAttributeValueException;
import java.math.BigDecimal;

public interface Account {

    void authorize();

    void replenishAccount(BigDecimal amount);

    boolean isAuthorized();

    String getPin();

    BigDecimal getBalance();

    BigDecimal withdraw(BigDecimal amount) throws IllegalArgumentException, InvalidAttributeValueException;


}
