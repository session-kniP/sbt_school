package com.sbt.school.terminal.app;

import com.sbt.school.terminal.exceptions.PinFormatException;

import javax.security.auth.login.AccountLockedException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.InputMismatchException;

public interface Terminal {

    boolean authorize(String pin) throws IOException, PinFormatException, InputMismatchException, AccountLockedException;

    BigDecimal checkBalance();

    boolean replenishAccount(BigDecimal amount);

    BigDecimal withdraw(BigDecimal amount);

}
