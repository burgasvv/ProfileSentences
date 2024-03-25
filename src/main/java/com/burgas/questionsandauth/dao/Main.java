package com.burgas.questionsandauth.dao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class Main {

    public static void main(String[] args) {

        LocalDateTime from = LocalDateTime.of(2024, 3, 24, 12, 32);
        LocalDateTime to = LocalDateTime.now();

        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        Duration duration = Duration.between(from.toLocalTime(), to.toLocalTime());
    }
}
