package com.sbt.school.servlets.content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HeaderImpl implements Header<String, List<String>> {

    private final LocalDateTime dateTime;
    private final Map<String, List<String>> header;

    public HeaderImpl(Map<String, List<String>> header) {
        this.dateTime = LocalDateTime.now();
        this.header = header;
    }

    @Override
    public List<String> get(String key) {
        return header.get(key);
    }

    @Override
    public Map<String, List<String>> getAll() {
        return null;
    }

    @Override
    public LocalDateTime getCreationDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Caching date: ").append(dateTime).append("\n");
        sb.append("Header: \n");

        header.forEach((k, v) -> {
            sb.append(k == null ? "" : k).append(" : ").append(String.join(" | ", v)).append("\n");
        });

        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderImpl header1 = (HeaderImpl) o;
        return Objects.equals(header, header1.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }
}
