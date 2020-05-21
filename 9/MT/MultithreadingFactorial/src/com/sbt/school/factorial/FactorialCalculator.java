package com.sbt.school.factorial;

import com.sbt.school.utils.NumberAndFactorial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FactorialCalculator {

    private final String path;
    private final String name;

    private final File file;


    public FactorialCalculator(String path, String name) {
        if (!path.endsWith("/")) {
            this.path = path + "/";
        } else {
            this.path = path;
        }
        this.name = name;
        this.file = new File(path + name);
    }


    public List<NumberAndFactorial> calcFactorialsAsync() throws IOException, NumberFormatException, InterruptedException {

        List<Integer> numbers = loadNumbers();
        List<NumberAndFactorial> factorials = new ArrayList<>(numbers.size());

        for (Integer number : numbers) {
            Thread t = new Thread(() -> factorials.add(new NumberAndFactorial(number, factorial(number))));
            t.start();
            t.join();
        }

        return factorials;
    }

    public List<NumberAndFactorial> calcFactorials() throws IOException, NumberFormatException {

        List<Integer> numbers = loadNumbers();
        List<NumberAndFactorial> factorials = new ArrayList<>(numbers.size());

        for (Integer number : numbers) {
            factorials.add(new NumberAndFactorial(number, factorial(number)));
        }

        return factorials;
    }



    public List<Integer> loadNumbers() throws FileNotFoundException {

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }

        List<Integer> numbers = new ArrayList<>();

        while (scanner.hasNextLine()) {
            try {
                int number = Integer.parseInt(scanner.nextLine());

                numbers.add(number);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Reading number error: " + e.getMessage());
            }
        }

        return numbers;
    }


    private BigInteger factorial(Integer number) {
        if (number == 0) {
            return BigInteger.ONE;
        }

        BigInteger result = BigInteger.ONE;

        while (number > 0) {
            result = result.multiply(BigInteger.valueOf(number));
            number--;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


}



