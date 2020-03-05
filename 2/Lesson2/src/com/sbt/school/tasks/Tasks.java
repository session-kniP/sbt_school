package com.sbt.school.tasks;

import java.io.*;
import java.util.*;

public abstract class Tasks {

    public static String filesDir = System.getProperty("user.dir") + "\\files\\";


    //Если не считать, что цифры и знаки препинания являюстя словами + слова регистро независимые
    public static Integer task1(String filename) {
        FileInputStream fstream = null;
        try {
            System.out.println("\n=============TASK1===============\n");
            fstream = new FileInputStream(new File(filesDir + filename));
            Scanner scanner = new Scanner(fstream);
            HashSet<String> hashSet = new HashSet<>();

            while (scanner.hasNext()) {
                String s = scanner.next();
                if (Character.isLetter(s.toCharArray()[0])) {
                    hashSet.add(s.toUpperCase());
                }
            }

            return hashSet.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void task2(String filename) {
        FileInputStream fstream = null;
        try {
            System.out.println("\n=============TASK2===============\n");

            fstream = new FileInputStream(new File(filesDir + filename));


            Scanner scanner = new Scanner(fstream);
            HashSet<String> hashSet = new HashSet<>();

            while (scanner.hasNext()) {
                String s = scanner.next();
                if (Character.isLetter(s.toCharArray()[0])) {
                    hashSet.add(s.toLowerCase());
                }
            }

            ArrayList<String> words = new ArrayList<>(hashSet);
            Collections.sort(words, Comparator.comparingInt(String::length));

            System.out.println("Sort by length:");
            words.forEach((w) -> System.out.print(w + " | "));
            System.out.println();

            Collections.sort(words);

            System.out.println("Sort by length:");
            words.forEach((w) -> System.out.print(w + " | "));
            System.out.println();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void task3(String filename) {
        FileInputStream fstream = null;
        try {
            System.out.println("\n=============TASK3===============\n");

            fstream = new FileInputStream(new File(filesDir + filename));
            Scanner scanner = new Scanner(fstream);


            HashMap<String, Integer> quants = new HashMap<>();

            while (scanner.hasNext()) {
                String next = scanner.next();
                if (Character.isLetter(next.toCharArray()[0])) {
                    Integer howMuch;
                    if ((howMuch = quants.put(next, 1)) != null) {
                        quants.replace(next, howMuch + 1);
                    }
                }
            }

            quants.forEach((k, v) -> System.out.println(k + " | " + v + " times"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void task4_1(String filename) {

        FileInputStream fstream = null;
        try {
            System.out.println("\n=============TASK4_1===============\n");
            System.out.println("Strings fully reversed (per character)\n");

            fstream = new FileInputStream(new File(filesDir + filename));
            Scanner scanner = new Scanner(fstream);

            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                for (int i = str.length() - 1; i >= 0; i--) {
                    System.out.print(str.charAt(i));
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void task4_2(String filename) {

        FileInputStream fstream = null;
        try {
            System.out.println("\n=============TASK4_2===============\n");
            System.out.println("Strings reversed per words\n");

            fstream = new FileInputStream(new File(filesDir + filename));
            Scanner scanner = new Scanner(fstream);


            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                StringTokenizer st = new StringTokenizer(str);

                String reversed = "";

                while (st.hasMoreTokens()) {
                    reversed = st.nextToken() + " " + reversed;
                }

                System.out.println(reversed);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void task6(String filename) {
        FileInputStream fstream = null;
        try {
            System.out.println("\n=============TASK4_2===============\n");
            System.out.println("Strings reversed per words\n");

            fstream = new FileInputStream(new File(filesDir + filename));
            Scanner scanner = new Scanner(fstream);

            ArrayList<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            System.out.println("Количество строк в файле = " + lines.size());
            System.out.print("Введите номера выводимых строк через пробел (начиная с 1): ");

            BufferedReader stream = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(stream.readLine());

            ArrayList<Integer> lineNumbers = new ArrayList<>();

            while (st.hasMoreTokens()) {
                try {
                    int number = Integer.parseInt(st.nextToken());
                    if (number > lines.size()) {
                        throw new IndexOutOfBoundsException("Номер строки не должен превышать количество возможных строк");
                    }
                    lineNumbers.add(number);
                } catch (NumberFormatException e) {
                    System.out.println("НЕВЕРЫЙ ФОРМАТ ДАННЫХ! НЕОБХОДИМО ВВОДИТЬ ЦЕЛЫЕ ЧИСЛА!");
                    return;
                }
            }

            for (int i : lineNumbers) {
                System.out.println(lines.get(i - 1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
