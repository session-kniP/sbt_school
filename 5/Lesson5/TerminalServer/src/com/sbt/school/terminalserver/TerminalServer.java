package com.sbt.school.terminalserver;

import javax.security.auth.login.AccountLockedException;
import java.math.BigDecimal;

public interface TerminalServer {

    boolean authorize(String pin) throws AccountLockedException;

    BigDecimal getBalance(String pin);

    boolean replenishAccount(String pin, BigDecimal amount) throws IllegalArgumentException;

    BigDecimal withdraw(String pin, BigDecimal amount) throws IllegalArgumentException;


}
