package com.sbt.school.factorial;

import java.io.*;
import java.util.Random;

public class RandomFiller {

    private final String path;
    private final String name;

    private final int numbersCount;
    private final int min, max;
    private final int diff;

    private final File file;


    public RandomFiller(String path, String name, int numbersCount, int min, int max) {
        if(!path.endsWith("/")) {
            this.path = path + "/";
        } else {
            this.path = path;
        }

        this.name = name;
        this.numbersCount = numbersCount;
        this.min = min;
        this.max = max;
        this.diff = max - min;

        this.file = new File(this.path + name);
    }


    public void fill() throws IOException {
        try {
            checkFile();
        } catch (IOException e) {
            throw new IOException("Problems with file creating");
        }

        fillWithRandom();
    }


    private boolean checkFile() throws IOException {
        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

    }


    private void fillWithRandom() throws IOException {

        try (PrintStream ps = new PrintStream(file)) {
            //cleaning up a file
//            fw.write("");
            Random random = new Random();

            //filling in random between min and max
            for (int i = 0; i < numbersCount; i++) {
                ps.println(random.nextInt(diff) + min);
            }

        } catch (IOException e) {
            throw new IOException("Problems with opening file writer");
        }
    }


}
