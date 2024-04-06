package com.burgas.profilesentences.filter;

import com.burgas.profilesentences.dao.UserDao;
import com.burgas.profilesentences.dao.UserRoleDao;
import com.burgas.profilesentences.entity.Role;
import com.burgas.profilesentences.entity.User;
import com.burgas.profilesentences.entity.UserRole;
import com.burgas.profilesentences.handler.PasswordHandler;
import com.burgas.profilesentences.manager.PropertiesManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static java.lang.System.out;

@WebFilter(filterName = "registrationFilter",
        servletNames = "registrationServlet", urlPatterns = "/registration-servlet")
public class RegistrationFilter implements Filter {

    public static final String PARAMETER_NAME_OF_USERNAME = "userName";
    public static final String PARAMETER_NAME_OF_EMAIL = "email";
    public static final String REGISTRATION_JSP_PROPERTY_NAME = "registrationJsp";
    public static final String PARAMETER_NAME_OF_PASSWORD = "password";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //noinspection StringTemplateMigration
        out.println(this.getClass().getSimpleName() + " initialized");
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
                                PropertiesManager.fileProperties().getProperty(REGISTRATION_JSP_PROPERTY_NAME)
                        ).forward(request, response);

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

        //noinspection StringTemplateMigration
        out.println(this.getClass().getSimpleName() + " destroyed");
    }
}
