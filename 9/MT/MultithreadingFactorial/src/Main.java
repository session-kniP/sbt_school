import com.sbt.school.factorial.FactorialCalculator;
import com.sbt.school.factorial.RandomFiller;
import com.sbt.school.utils.NumberAndFactorial;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        if (args.length == 1 && args[0].equals("-h")) {
            System.out.println("Input string format:");
            System.out.println("[String:path] [String:Filename] [int:numbersCount] [int:min] [int:max]");
        }

        try {

            RandomFiller filler = new RandomFiller(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            filler.fill();

            FactorialCalculator calculator = new FactorialCalculator(args[0], args[1]);

            try {

                List<NumberAndFactorial> integers;

                integers = calculator.calcFactorialsAsync();

                for (NumberAndFactorial number : integers) {
                    System.out.println(number);
                }

            } catch (IOException e) {
                throw e;
            }
        } catch (IOException | IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println(e.getMessage());
            System.out.println("Type -h for help");
        }

    }

}
