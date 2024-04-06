package com.burgas.profilesentences.servlet;

import com.burgas.profilesentences.manager.PropertiesManager;
import com.burgas.profilesentences.util.Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "logOutServlet", urlPatterns = "/log_out-servlet")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(
                PropertiesManager.fileProperties().getProperty(Util.LOGOUT_PAGE_JSP_PROPERTY_NAME)
        ).forward(req, resp);
    }
}
