package com.sbt.school.terminalserver.users;

import java.math.BigDecimal;

public class HardCodedUser implements Account {

    private final String pin = "1542";
    private boolean authorized;
    private BigDecimal balance;

    public HardCodedUser(BigDecimal balance) {
        this.authorized = false;
        this.balance = balance;
    }

    @Override
    public void authorize() {
        this.authorized = true;
    }

    @Override
    public void replenishAccount(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    @Override
    public boolean isAuthorized() {
        return authorized;
    }

    @Override
    public String getPin() {
        return pin;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public BigDecimal withdraw(BigDecimal amount) throws IllegalArgumentException {
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the account");
        }
        this.balance = newBalance;
        return amount;
    }

}
