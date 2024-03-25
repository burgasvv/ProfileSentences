package com.burgas.questionsandauth.filter;

import com.burgas.questionsandauth.dao.UserDao;
import com.burgas.questionsandauth.dao.UserRoleDao;
import com.burgas.questionsandauth.entity.Role;
import com.burgas.questionsandauth.entity.User;
import com.burgas.questionsandauth.entity.UserRole;
import com.burgas.questionsandauth.handler.PasswordHandler;
import com.burgas.questionsandauth.manager.PropertiesManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@WebFilter(filterName = "registrationFilter",
        servletNames = "registrationServlet", urlPatterns = "/registration-servlet")
public class RegistrationFilter implements Filter {

    public static final String PARAMETER_NAME_OF_USERNAME = "userName";
    public static final String PARAMETER_NAME_OF_EMAIL = "email";
    public static final String REGISTRATION_JSP_PROPERTY_NAME = "registrationJsp";
    public static final String PARAMETER_NAME_OF_PASSWORD = "password";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String userName = request.getParameter(PARAMETER_NAME_OF_USERNAME);
        String email = request.getParameter(PARAMETER_NAME_OF_EMAIL);
        String password = request.getParameter(PARAMETER_NAME_OF_PASSWORD);

        UserDao userDao = new UserDao();
        UserRoleDao userRoleDao = new UserRoleDao();
        List<User> users = userDao.getAll();

        users.stream()
                .filter(
                userData -> userData.getUserName().equals(userName) ||
                        userData.getEmail().equals(email)
        )
                .findAny()
                .ifPresentOrElse(
                        _ -> {
                    try {
                        request.getRequestDispatcher(
                                PropertiesManager.fileProperties().getProperty(REGISTRATION_JSP_PROPERTY_NAME))
                                .forward(request, response);

                    } catch (IOException | ServletException e) {
                        throw new RuntimeException(e);
                    }
                },
                        () -> {
                    try {
                        userDao.insert(new User(userName, email, PasswordHandler.hashPassword(password)));
                        List<User> all = userDao.getAll();
                        userRoleDao.insert(
                                new UserRole(
                                        new User(all.getLast().getId()), new Role(1)
                                )
                        );

                        filterChain.doFilter(request, response);

                    } catch (IOException | NoSuchAlgorithmException | ServletException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
