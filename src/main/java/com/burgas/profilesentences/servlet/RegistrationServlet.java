package com.burgas.profilesentences.servlet;

import com.burgas.profilesentences.manager.PropertiesManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "registrationServlet", urlPatterns = "/registration-servlet")
public class RegistrationServlet extends HttpServlet {

    public static final String INDEX_JSP_PROPERTY_NAME = "indexJsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect(PropertiesManager.fileProperties().getProperty(INDEX_JSP_PROPERTY_NAME));
    }
}
