package com.burgas.profilesentences.servlet;

import com.burgas.profilesentences.manager.PropertiesManager;
import com.burgas.profilesentences.service.TextService;
import com.burgas.profilesentences.util.Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.String.join;
import static java.net.URLEncoder.encode;

@WebServlet(name = "questionsServlet", urlPatterns = "/questions-servlet")
public class QuestionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TextService textService = new TextService();
        List<String> questions = textService.findQuestions(textService.getText(
                PropertiesManager.fileProperties().getProperty(Util.TEXT_FILE_PROPERTY_NAME)
        ));

        req.setAttribute(Util.COOKIE_ATTRIBUTE_NAME_OF_TEXT, textService.getText(
                PropertiesManager.fileProperties().getProperty(Util.TEXT_FILE_PROPERTY_NAME)
        ));
        req.setAttribute(Util.ATTRIBUTE_NAME_OF_QUESTIONS, questions);

        Cookie cookie = new Cookie(Util.COOKIE_NAME_OF_QUESTIONS,
                encode(join(Util.DELIMITER, questions), StandardCharsets.UTF_8));
        cookie.setMaxAge(Util.DURATION_DURING_THE_SESSION_VALUE);
        resp.addCookie(cookie);

        req.getRequestDispatcher(PropertiesManager.fileProperties().getProperty(Util.TEXT_QUESTIONS_JSP_PROPERTY_NAME))
                .forward(req, resp);
    }
}