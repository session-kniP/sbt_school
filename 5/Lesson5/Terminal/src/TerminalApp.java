import com.sbt.school.terminal.app.MenuOptions;
import com.sbt.school.terminal.app.TerminalImpl;
import com.sbt.school.terminal.app.validators.DecimalValidator;
import com.sbt.school.terminalserver.TerminalServerImpl;

import javax.security.auth.login.AccountLockedException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class TerminalApp implements Runnable {

    private final TerminalImpl terminal;
    private final DecimalValidator decimalValidator;

    public TerminalApp(TerminalServerImpl server) {
        this.terminal = new TerminalImpl(server);
        this.decimalValidator = new DecimalValidator();
    }


    @Override
    public void run() {

        //main menu representation

        while (true) {

            while (true) {
                while (!terminal.localUserAuthorized()) {
                    try {
                        System.out.print("Enter 4-digits PIN:\t");
                        String pin = new Scanner(System.in).nextLine();
                        terminal.authorize(pin);
                    } catch (RuntimeException | AccountLockedException e) {
                        Optional<Throwable> first = Stream.iterate(e, Throwable::getCause)
                                .filter(cause -> cause.getCause() == null).findFirst();
                        System.out.println(first.get().getMessage());
                    }
                }

                try {
                    switch (getMainMenuOp()) {

                        case MenuOptions.CHECK_BALANCE:

                            BigDecimal balance = terminal.checkBalance();

                            System.out.println(String.format("Your balance: %.2f c.u.", balance.floatValue()));

                            break;

                        case MenuOptions.REPLENISH_ACCOUNT:

                            while (true) {
                                try {
                                    printReplenishMenu();

                                    String stringNumber = new Scanner(System.in).nextLine();
                                    boolean valid = decimalValidator.validate(stringNumber);
                                    if (valid) {
                                        if (Double.parseDouble(stringNumber) == BigDecimal.ZERO.doubleValue()) {
                                            break;
                                        }
                                        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(stringNumber));
                                        terminal.replenishAccount(amount);
                                        System.out.println(String.format("Account replenished %.2f c.u.", amount.floatValue()));

                                        break;
                                    }
                                } catch (SecurityException | IllegalArgumentException e) {
                                    Optional<Throwable> rootException = Stream.iterate(e, Throwable::getCause)
                                            .filter(el -> el.getCause() == null).findFirst();

                                    System.out.println(rootException.get().getMessage());
                                }
                            }

                            System.out.println("Success");

                            break;


                        case MenuOptions.WITHDRAW:

                            while (true) {
                                try {

                                    printWithdrawMenu();

                                    String stringNumber = new Scanner(System.in).nextLine();

                                    boolean valid = decimalValidator.validate(stringNumber);

                                    if (valid) {
                                        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(stringNumber));
                                        System.out.println(String.format("Withdrawn %.2f", terminal.withdraw(amount).floatValue()));
                                        break;
                                    }
                                } catch (SecurityException | IllegalArgumentException e) {
                                    Optional<Throwable> rootException = Stream.iterate(e, Throwable::getCause)
                                            .filter(el -> el.getCause() == null).findFirst();

                                    System.out.println(rootException.get().getMessage());
                                }
                            }

                            System.out.println("Success");

                            break;

                        case MenuOptions.EXIT:

                            try {
                                terminal.deauthorize();
                            } catch (SecurityException e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                        default:
                            System.out.println("Operation not supported, choose another operation");
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println(e.getMessage());
                }

            }

        }
    }


    private void printMainMenu() {
        System.out.println("\n===========MAIN MENU===========\n");

        System.out.println("Choose operation: ");
        System.out.println("1. Check balance");
        System.out.println("2. Replenish Account");
        System.out.println("3. Withdraw");
        System.out.println("0. Exit");
    }


    private void printReplenishMenu() {
        System.out.println("\n===========REPLENISH ACCOUNT===========\n");

        System.out.print("Enter sum (0 for exit): ");
    }

    private void printWithdrawMenu() {
        System.out.println("\n===========WITHDRAW===========\n");

        System.out.print("Enter sum (0 for exit): ");
    }


    private int getMainMenuOp() throws IOException, NumberFormatException {
        printMainMenu();

        int op;

        try {
            op = Integer.parseInt(String.valueOf((char) System.in.read()));
            System.in.read();
        } catch (IOException e) {
            throw e;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid input format. Input a number");
        }

        return op;
    }


}
