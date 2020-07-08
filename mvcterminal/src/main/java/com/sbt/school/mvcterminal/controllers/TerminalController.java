package com.sbt.school.mvcterminal.controllers;


import com.sbt.school.mvcterminal.bl.TerminalWrapper;
import com.sbt.school.terminal.exceptions.PinFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.auth.login.AccountLockedException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Controller
public class TerminalController {

    @Autowired
    private TerminalWrapper terminal;

    @GetMapping("/")
    public String main(
            @RequestParam(name = "error_msg", required = false) String errorMsg,
            @RequestParam(name = "success_msg", required = false) String successMsg,
            Map<String, Object> dom
    ) {
        dom.put("error_msg", errorMsg);
        dom.put("success_msg", successMsg);

        return "auth";
    }

    @PostMapping("/auth")
    public String auth(
            @RequestParam(name = "pin") String pin,
            RedirectAttributes redirect,
            Map<String, Object> dom
    ) {
        if (terminal.getTerminal().localUserAuthorized()) {
            return "redirect:/menu";
        }

        try {
            terminal.getTerminal().authorize(pin);
            return "redirect:/menu";
        } catch (AccountLockedException | PinFormatException | SecurityException e) {
            System.out.println("Wrong");
            redirect.addAttribute("error_msg", getRootExceptionMessage(e));
            return getBaseRedirect();
        }
    }

    @GetMapping("/menu")
    public String menu(@RequestParam(name = "error_msg", required = false) String errorMsg,
                       @RequestParam(name = "success_msg", required = false) String successMsg,
                       Map<String, Object> dom) {
        System.out.println("Menu");
        if (!terminal.getTerminal().localUserAuthorized()) {
            return getBaseRedirect();
        }

        dom.put("error_msg", errorMsg);
        dom.put("success_msg", successMsg);

        Map<String, String> menuMap = new HashMap<>();

        menuMap.put("Check Balance", "/check_balance");
        menuMap.put("Replenish", "/replenish");
        menuMap.put("Withdraw", "/withdraw");
        menuMap.put("End session", "/disconnect");

        dom.put("main_menu", menuMap.entrySet());
        dom.put("back_btn", "Back to Menu");

        return "menu";
    }


    @GetMapping("/check_balance")
    public String checkBalance(Map<String, Object> dom) {
        if (!terminal.getTerminal().localUserAuthorized()) {
            return getBaseRedirect();
        }

        dom.put("balance", String.format("%.2f c.u.", terminal.getTerminal().checkBalance().floatValue()));
        dom.put("back_btn", "Back to Menu");
        return "balance";
    }


    @GetMapping("/withdraw")
    public String withdraw(@RequestParam(name = "error_msg", required = false) String errorMsg,
                           @RequestParam(name = "success_msg", required = false) String successMsg,
                           Map<String, Object> dom) {
        if (!terminal.getTerminal().localUserAuthorized()) {
            return getBaseRedirect();
        }

        dom.put("error_msg", errorMsg);
        dom.put("success_msg", successMsg);

        dom.put("values_allowed", terminal.getTerminal().getValueAllowed().floatValue());
        dom.put("back_btn", "Back to Menu");

        return "withdrawing";
    }

    @PostMapping("/takeMoney")
    public String takeMoney(@RequestParam(name = "amount") String amount, RedirectAttributes attributes) {
        if (!terminal.getTerminal().localUserAuthorized()) {
            return getBaseRedirect();
        }

        try {
            BigDecimal decimalAmount;
            try {
                decimalAmount = BigDecimal.valueOf(Double.parseDouble(amount));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Amount should be a number");
            }
            terminal.getTerminal().withdraw(decimalAmount);
            attributes.addAttribute("success_msg", String.format("You successfully withdrawn %.2f c.u.", decimalAmount.floatValue()));
            return "redirect:/menu";
        } catch (SecurityException | IllegalArgumentException e) {
            attributes.addAttribute("error_msg", getRootExceptionMessage(e));
            return "redirect:/withdraw";
        }
    }


    @GetMapping("/replenish")
    public String replenish(@RequestParam(name = "error_msg", required = false) String errorMsg,
                            @RequestParam(name = "success_msg", required = false) String successMsg,
                            Map<String, Object> dom) {
        if (!terminal.getTerminal().localUserAuthorized()) {
            return getBaseRedirect();
        }

        dom.put("error_msg", errorMsg);
        dom.put("success_msg", successMsg);
        dom.put("back_btn", "Back to Menu");

        return "replenishment";
    }


    @PostMapping("/putMoney")
    public String putMoney(@RequestParam(name = "amount") String amount, RedirectAttributes attributes) {
        if (!terminal.getTerminal().localUserAuthorized()) {
            return getBaseRedirect();
        }

        try {
            BigDecimal decimalAmount;
            try {
                decimalAmount = BigDecimal.valueOf(Double.parseDouble(amount));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Amount should be a number");
            }
            terminal.getTerminal().replenishAccount(decimalAmount);
            attributes.addAttribute("success_msg", String.format("You successfully replenished %.2f c.u.", decimalAmount.floatValue()));
            return "redirect:/menu";
        } catch (SecurityException | IllegalArgumentException e) {
            attributes.addAttribute("error_msg", getRootExceptionMessage(e));
            return "redirect:/replenish";
        }
    }


    @GetMapping("/disconnect")
    public String disconnect() {
        terminal.getTerminal().deauthorize();
        return getBaseRedirect();
    }


    private String getBaseRedirect() {
        return "redirect:/";
    }

    private String getRootExceptionMessage(Throwable e) {
        return Stream.iterate(e, Throwable::getCause)
                .filter(cause -> cause.getCause() == null).findFirst().get().getMessage();
    }

}
