package com.burgas.questionsandauth.servlet;

import com.burgas.questionsandauth.manager.PropertiesManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.net.URLEncoder.encode;

@WebServlet(name = "questionsServlet", urlPatterns = "/questions-servlet")
public class QuestionsServlet extends HttpServlet {

    public static final String TEXT_FILE_PROPERTY_NAME = "textFile";
    public static final String TEXT_QUESTIONS_JSP_PROPERTY_NAME = "textQuestionsJsp";
    public static final String DELIMITER = "▬▬▬▬";
    public static final String COOKIE_NAME_OF_QUESTIONS = "questionsCookie";
    public static final String ATTRIBUTE_NAME_OF_QUESTIONS = "questionsAttribute";
    public static final String COOKIE_ATTRIBUTE_NAME_OF_TEXT = "text";
    public static final int DURATION_DURING_THE_SESSION_VALUE = -1;
    private BufferedReader bufferedReader;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> questions = new ArrayList<>();

        findQuestions(getText(), questions);
        processRequest(req, questions);
        addCookie(resp, createCookie(questions));
        forwardRequest(req, resp);
    }

    private void addCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setMaxAge(DURATION_DURING_THE_SESSION_VALUE);
        response.addCookie(cookie);
    }

    private Cookie createCookie(List<String> questions) {
        return new Cookie(
                COOKIE_NAME_OF_QUESTIONS, encode(getQuestionsString(questions), StandardCharsets.UTF_8)
        );
    }

    private String getQuestionsString(List<String> questions) {
        return String.join(DELIMITER, questions);
    }

    private void forwardRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher(PropertiesManager.fileProperties().getProperty(TEXT_QUESTIONS_JSP_PROPERTY_NAME))
                .forward(req, resp);
    }

    private void processRequest(HttpServletRequest req, List<String> questions) throws FileNotFoundException {
        req.setAttribute(COOKIE_ATTRIBUTE_NAME_OF_TEXT, getText());
        req.setAttribute(ATTRIBUTE_NAME_OF_QUESTIONS, questions);
    }

    private void findQuestions(String text, List<String> questions) {

        char[] chars = text.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        for (char i = 0; i < chars.length; i++) {

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
        }
    }

    private String getText() throws FileNotFoundException {

        bufferedReader = new BufferedReader(
                new FileReader(PropertiesManager.fileProperties().getProperty(TEXT_FILE_PROPERTY_NAME))
        );

        return bufferedReader.lines().collect(Collectors.joining());
    }

    @Override
    public void destroy() {
        try {
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}