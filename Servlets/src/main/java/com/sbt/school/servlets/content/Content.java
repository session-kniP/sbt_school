package com.sbt.school.servlets.content;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Content {

    private final int statusCode;

    private final Header<String, List<String>> header;

    private final String content;

    public Content(int statusCode, Map<String, List<String>> header, String content) {
        this.statusCode = statusCode;
        this.header = new HeaderImpl(header);
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    @Override
    public String toString() {

        return "Status code: " + statusCode +
                header.toString() +
                content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content1 = (Content) o;
        return statusCode == content1.statusCode &&
                Objects.equals(header, content1.header) &&
                Objects.equals(content, content1.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, header, content);
    }
}
