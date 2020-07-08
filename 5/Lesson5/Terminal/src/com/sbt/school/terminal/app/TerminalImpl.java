package com.sbt.school.terminal.app;

import com.sbt.school.terminal.app.validators.PinValidator;
import com.sbt.school.terminal.exceptions.PinFormatException;
import com.sbt.school.terminal.user.LocalHardCodedUser;
import com.sbt.school.terminalserver.TerminalServerImpl;

import javax.security.auth.login.AccountLockedException;
import java.math.BigDecimal;
import java.text.ParseException;

public class TerminalImpl implements Terminal {

    private final PinValidator validator;
    private final TerminalServerImpl server;
    private LocalHardCodedUser user;

    public TerminalImpl(TerminalServerImpl server) {
        this.validator = new PinValidator();
        this.server = server;
    }


    public boolean authorize(String pin) throws AccountLockedException, PinFormatException, SecurityException {

        try {
            boolean valid = validator.validate(pin);
            if (valid) {
                boolean authorized = this.server.authorize(pin);

                if (authorized) {
                    this.user = new LocalHardCodedUser(pin);
                }
            }
        } catch (PinFormatException e) {
            throw new PinFormatException("PIN format error", e);
        } catch (SecurityException e) {
            throw new SecurityException("Permissions denied", e);
        }

        return true;

    }


    public boolean deauthorize() {
        if (user != null) {
            user = null;
            return true;
        } else {
            throw new SecurityException("User not authorized");
        }
    }


    @Override
    public BigDecimal checkBalance() throws SecurityException {
        if (localUserAuthorized()) {
            try {
                return server.getBalance(user.getPin());
            } catch (SecurityException e) {
                throw new SecurityException("Balance checking security exception", e);
            }
        }
        throw new SecurityException("User should be authorized");
    }

    @Override
    public boolean replenishAccount(BigDecimal amount) throws SecurityException, IllegalArgumentException {
        if (localUserAuthorized()) {
            try {
                server.replenishAccount(user.getPin(), amount);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Server replenish account error", e);
            }
            return true;
        }
        throw new SecurityException("User should be authorized");
    }

    @Override
    public BigDecimal withdraw(BigDecimal amount) throws SecurityException, IllegalArgumentException {
        if (localUserAuthorized()) {
            try {
                BigDecimal withdrawn = server.withdraw(user.getPin(), amount);
                return withdrawn;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Server withdraw error", e);
            }
        }
        throw new SecurityException("User should be authorized");
    }


    public boolean localUserAuthorized() {
        return user != null;
    }

    @Override
    public BigDecimal getValueAllowed() {
        if (localUserAuthorized()) {
            return server.getValueAllowed();
        }
        throw new SecurityException("User should be authorized");
    }

}
