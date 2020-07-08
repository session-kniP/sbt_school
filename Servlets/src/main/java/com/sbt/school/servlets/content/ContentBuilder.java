package com.sbt.school.servlets.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class ContentBuilder {

    public static String build(BufferedReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        String temp;

        try {
            while ((temp = reader.readLine()) != null) {
                result.append(temp).append("\n");
            }

            return result.toString();
        } catch (IOException e) {
            throw new IOException("Buffered Reader: Can't read line", e);
        }
    }
}
