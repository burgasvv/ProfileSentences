package com.burgas.profilesentences.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class TextService {

    public List<String> findQuestions(String text) {

        List<String> questions = new ArrayList<>();
        char[] chars = text.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        range(0, chars.length).forEach(i -> {

            stringBuilder.append(chars[i]);

            if (stringBuilder.toString().charAt(0) == ' ')
                stringBuilder.deleteCharAt(0);

            if (chars[i] == '.' || chars[i] == '!' || chars[i] == ';' || chars[i] == '…')
                stringBuilder.delete(0, stringBuilder.length());

            if (chars[i] == '?') {

                if (chars[i + 1] == ')' || chars[i + 1] == '}' ||
                        chars[i + 1] == ']' || chars[i + 1] == '"' ||
                        chars[i + 1] == '\'' || chars[i + 1] == '!') {

                    stringBuilder.append(chars[i + 1]);
                }

                questions.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
        });

        return questions;
    }

    public String getText(String name) throws FileNotFoundException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(name));
        return bufferedReader.lines().collect(Collectors.joining());
    }
}
