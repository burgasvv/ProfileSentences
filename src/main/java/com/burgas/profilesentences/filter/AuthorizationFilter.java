package com.burgas.profilesentences.filter;

import com.burgas.profilesentences.dao.UserRoleDao;
import com.burgas.profilesentences.entity.Role;
import com.burgas.profilesentences.entity.UserRole;
import com.burgas.profilesentences.entity.UserType;
import com.burgas.profilesentences.handler.PasswordHandler;
import com.burgas.profilesentences.entity.User;
import com.burgas.profilesentences.manager.PropertiesManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebFilter(filterName = "authorizationFilter",
        servletNames = "authorizationServlet", urlPatterns = "/authorization-servlet")
public class AuthorizationFilter implements Filter {

    public static final String AUTHORIZATION_JSP_PROPERTY_NAME = "authorizationJsp";
    public static final String PARAMETER_NAME_OF_USER_NAME_OR_EMAIL = "userName_email";
    public static final String PARAMETER_NAME_OF_PASSWORD = "password";
    public static final String ATTRIBUTE_NAME_OF_USERNAME = "userName";
    public static final String PARAMETER_NAME_OF_EMAIL = "email";
    public static final String ATTRIBUTE_NAME_OF_LOGINTIME = "loginTime";
    public static final String ATTRIBUTE_NAME_OF_ROLENAME = "roleName";
    public static final String ADMIN_JSP_PROPERTY_NAME = "adminJsp";
    public static final String ROLENAME_USER = "user";
    public static final String ROLENAME_ADMIN = "admin";
    public static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String userNameEmail = request.getParameter(PARAMETER_NAME_OF_USER_NAME_OR_EMAIL);
        String password = request.getParameter(PARAMETER_NAME_OF_PASSWORD);
        HttpSession session = request.getSession();

        UserRoleDao userRoleDao = new UserRoleDao();
        List<UserRole> all = userRoleDao.getAll();

        all.stream().filter(
                userRole -> {
            try {
                return (userNameEmail.equals(userRole.getUser().getUserName()) ||
                        userNameEmail.equals(userRole.getUser().getEmail())) &&
                        userRole.getUser().getPassword().equals(PasswordHandler.hashPassword(password));

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        }).findFirst().ifPresentOrElse(

                userRole -> {
                    try {

                        if (userRole.getRole().getName().equals(ROLENAME_ADMIN)) {

                            UserType userType = UserType.ADMIN;
                            String userName = userRole.getUser().getUserName();
                            String roleName = userRole.getRole().getName();
                            String email = userRole.getUser().getEmail();

                            processSession(session, roleName, userType, userName, email, password);
                            request.getRequestDispatcher(
                                    PropertiesManager.fileProperties().getProperty(ADMIN_JSP_PROPERTY_NAME))
                                    .forward(request, response);
                            filterChain.doFilter(request, response);

                        } else  {

                            userRoleDao.update(
                                    new UserRole(
                                            new User(userRole.getUser().getId()),
                                            new Role(2)
                                    )
                            );

                            UserType userType = UserType.USER;
                            String userName = userRole.getUser().getUserName();
                            String email = userRole.getUser().getEmail();

                            processSession(session, ROLENAME_USER, userType, userName, email, password);

                            filterChain.doFilter(request, response);
                        }

                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }

                }, () -> {

                    try {
                        response.sendRedirect(
                                PropertiesManager.fileProperties().getProperty(AUTHORIZATION_JSP_PROPERTY_NAME));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

    }

    private void processSession(HttpSession session, String roleName, UserType userType,
                                       String userName, String email, String password) {

        session.setAttribute(ATTRIBUTE_NAME_OF_ROLENAME, roleName);
        session.setAttribute(SecurityFilter.ATTRIBUTE_NAME_OF_USERTYPE, userType);
        session.setAttribute(ATTRIBUTE_NAME_OF_USERNAME, userName);
        session.setAttribute(PARAMETER_NAME_OF_EMAIL, email);
        session.setAttribute(PARAMETER_NAME_OF_PASSWORD, password);
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
        session.setAttribute(ATTRIBUTE_NAME_OF_LOGINTIME, Timestamp.valueOf(format));
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
