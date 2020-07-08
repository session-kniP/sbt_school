package com.sbt.school.terminalserver;


import com.sbt.school.terminalserver.users.HardCodedUser;
import com.sbt.school.utils.Timer;

import javax.security.auth.login.AccountLockedException;
import java.math.BigDecimal;
import java.util.Random;

public class TerminalServerImpl implements TerminalServer {

    //let's imagine that we have the only user
    private final HardCodedUser user;
    private int wrongTriesAllowed;
    private final Timer timer;
    private BigDecimal valueAllowed;


    public TerminalServerImpl() {
        float randomBalance = new Random().nextFloat() * 900.0f + 100.0f;   //random balance in range of 100.0...1000.0 c.u.
        this.user = new HardCodedUser(BigDecimal.valueOf(randomBalance));
        this.wrongTriesAllowed = 3;
        this.timer = new Timer();
        this.valueAllowed = BigDecimal.valueOf(100.0f);
    }


    @Override
    public boolean authorize(String pin) throws SecurityException, AccountLockedException {
        if (wrongTriesAllowed == 0) {
            processLock();
        }
        if (!pin.equals(user.getPin())) {
            wrongTriesAllowed--;
            if (wrongTriesAllowed == 0) {
                processLock();
            } else {
                throw new SecurityException(String.format("Incorrect PIN. Please, try again. %d tries left", wrongTriesAllowed));
            }
        }

        this.user.authorize();
        return true;
    }


    private void processLock() throws AccountLockedException {
        if (!timer.isStarted()) {
            timer.start();
        }
        if (timer.isStarted()) {
            if (timer.elapsedSeconds() < 5) {
                throw new AccountLockedException("Account temporary locked. Please, try again later");
            } else {
                timer.stop();
                this.wrongTriesAllowed = 3;
            }
        }
    }


    @Override
    public BigDecimal getBalance(String pin) throws SecurityException {
        try {
            if (!validate(pin)) {
                throw new SecurityException("Incorrect PIN. Try again");
            }
            return user.getBalance();
        } catch (SecurityException e) {
            throw new SecurityException("Access denied", e);
        }
    }

    @Override
    public boolean replenishAccount(String pin, BigDecimal amount) throws SecurityException, IllegalArgumentException {
        try {
            if (!validate(pin)) {
                throw new SecurityException("Incorrect PIN. Try again");
            }
            if (!amountAllowed(amount)) {
                throw new IllegalArgumentException("The amount must be a multiple of 100");
            }
            this.user.replenishAccount(amount);
        } catch (SecurityException e) {
            throw new SecurityException("Access denied", e);
        }
        return true;
    }

    @Override
    public BigDecimal withdraw(String pin, BigDecimal amount) throws IllegalArgumentException {
        try {
            if (!validate(pin)) {
                throw new SecurityException("Incorrect PIN. Try again");
            }
            if (!amountAllowed(amount)) {
                throw new IllegalArgumentException("The amount must be a multiple of 100");
            }
            return this.user.withdraw(amount);
        } catch (SecurityException e) {
            throw new SecurityException("Access denied", e);
        }
    }


    private boolean validate(String pin) throws SecurityException {
        if (!user.isAuthorized()) {
            throw new SecurityException("Access denied. User should be authorized");
        }
        return pin.equals(user.getPin());
    }

    private boolean amountAllowed(BigDecimal amount) {
        return amount.doubleValue() % valueAllowed.doubleValue() == 0.0;
    }

    public BigDecimal getValueAllowed() {
        return valueAllowed;
    }

}
