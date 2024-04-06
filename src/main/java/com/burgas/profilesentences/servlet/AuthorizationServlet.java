package com.burgas.profilesentences.servlet;

import com.burgas.profilesentences.manager.PropertiesManager;
import com.burgas.profilesentences.util.Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "authorizationServlet", urlPatterns = "/authorization-servlet")
public class AuthorizationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(
                PropertiesManager.fileProperties().getProperty(Util.INDEX_JSP_PROPERTY_NAME)
        ).forward(req, resp);
    }
}
