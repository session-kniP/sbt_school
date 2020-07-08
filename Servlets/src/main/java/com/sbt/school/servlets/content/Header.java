package com.sbt.school.servlets.content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public interface Header<K, V> {

    V get(K key);

    Map<K, V> getAll();

    LocalDateTime getCreationDateTime();

}
