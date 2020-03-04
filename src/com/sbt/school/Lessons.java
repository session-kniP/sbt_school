package com.sbt.school;

import com.sbt.school.utils.Utils;
import javafx.util.Pair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Lessons {

    private Scanner scanner;

    public Lessons() {
        scanner = new Scanner(System.in);
    }

    //    ввод: standard
//    вывод: standard
    public void task2001() throws IOException {    // with integers

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = a + b;

        System.out.println(c);
    }


    public void task2002() throws IOException {     //with integers

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int count = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());
        if (st.countTokens() == count) {
            int sum = 0;

            while (st.hasMoreTokens()) {
                sum += Integer.parseInt(st.nextToken());
            }

            System.out.println(sum);
        }
    }


    public void task2006() throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int number = Integer.parseInt(st.nextToken());

        if (number > 0) {
            int inches = number / 3;
            int diff = number - inches * 3;

            if (diff >= 2) {
                inches++;
            }

            int foots = inches / 12;
            inches = inches - foots * 12;


            System.out.println(foots + " " + inches);
        }
    }

    public void task2018() throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int count = Integer.parseInt(st.nextToken());

        int i = 0;
        int[] sequence = new int[count];


        st = new StringTokenizer(in.readLine());

        if (st.countTokens() == count) {
            while (st.hasMoreTokens()) {
                sequence[i++] = Integer.parseInt(st.nextToken());
            }

            int sum = 0;
            int nextSwitch = 0;
            int lastSwitch = 0;
            boolean plusOp = false;

            for (i = 0; i < count; i++) {
                if (i == lastSwitch + nextSwitch) {
                    lastSwitch = i;
                    nextSwitch++;
                    plusOp = !plusOp;
                }

                if (plusOp) {
                    sum += sequence[i];
                } else {
                    sum -= sequence[i];
                }
            }

            System.out.println(sum);
        }
    }


    public void task2022() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int count = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());

        if (st.countTokens() == count) {

            int i = 0;
            int[] sequence = new int[count];

            while (st.hasMoreTokens()) {
                sequence[i++] = Integer.parseInt(st.nextToken());
            }

            int sum = 0;

            for (i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    if (i != j) {
                        if (sequence[j] % sequence[i] == 0) {
                            sum++;
                        }
                    }
                }
            }

            System.out.println(sum);
        }

    }

    public void task2024() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int count = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());

        if (st.countTokens() == count) {

            int i = 0;
            int[] sequence = new int[count];

            while (st.hasMoreTokens()) {
                sequence[i++] = Integer.parseInt(st.nextToken());
            }

            int sum = 0;
            int ptrLeft, ptrRight;
            ptrLeft = count / 2 - 1;

            if (count % 2 != 0) {
                ptrRight = count / 2 + 1;
            } else {
                ptrRight = ptrLeft + 1;
            }

            while (ptrLeft >= 0) {

                if (sequence[ptrLeft] != sequence[ptrRight]) {
                    sum++;
                }
                ptrLeft--;
                ptrRight++;
            }

            System.out.println(sum);
        }
    }


    public void task2044() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int count = Integer.parseInt(st.nextToken());

        char[] chars = new char[count];
        String[] strings = new String[count];

        for (int i = 0; i < count; i++) {
            st = new StringTokenizer(in.readLine());
            chars[i] = st.nextToken().charAt(0);
            strings[i] = st.nextToken();
        }

        st = new StringTokenizer(in.readLine());
        String input = st.nextToken();

        String result = "";
        String buffer = "";

        for (int i = 0; i < input.length(); i++) {
            buffer += input.charAt(i);
            for(int j = 0; j < strings.length; j++) {
                if (buffer.equals(strings[j])) {
                    result += chars[j];
                    buffer = "";
                }
            }
        }

        System.out.println(result);

    }


}
