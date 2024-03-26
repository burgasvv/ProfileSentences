package com.burgas.profilesentences.filter;

import com.burgas.profilesentences.entity.UserType;
import com.burgas.profilesentences.manager.PropertiesManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.lang.System.out;

@WebFilter(filterName = "securityFilter", urlPatterns = "/index.jsp")
public class SecurityFilter implements Filter {

    public static final String ATTRIBUTE_NAME_OF_USERTYPE = "userType";
    public static final String GUEST_JSP_PROPERTY_NAME = "guestJsp";
    public static final String INITIALIZING_THE_SECURITY_FILTER = "Initializing the Security filter";
    public static final String DESTROYING_THE_SECURITY_FILTER = "Destroying the Security filter";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        out.println(INITIALIZING_THE_SECURITY_FILTER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        UserType userType = (UserType) session.getAttribute(ATTRIBUTE_NAME_OF_USERTYPE);

        if (userType == null || userType == UserType.GUEST) {

            userType = UserType.GUEST;
            session.setAttribute(ATTRIBUTE_NAME_OF_USERTYPE, userType);
            request.getRequestDispatcher(
                    PropertiesManager.fileProperties().getProperty(GUEST_JSP_PROPERTY_NAME))
                    .forward(request, response);

        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

        out.println(DESTROYING_THE_SECURITY_FILTER);
    }
}
