package com.sbt.school.mvcterminal.bl;

import com.sbt.school.terminal.app.TerminalImpl;
import com.sbt.school.terminalserver.TerminalServerImpl;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class TerminalWrapper {

    @Getter(AccessLevel.PUBLIC)
    private final TerminalImpl terminal;

    public TerminalWrapper() {
        this.terminal = new TerminalImpl(new TerminalServerImpl());
    }
}
