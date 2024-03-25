package com.burgas.profilesentences.filter;

import com.burgas.profilesentences.entity.UserType;
import com.burgas.profilesentences.manager.PropertiesManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "securityFilter", urlPatterns = "/index.jsp")
public class SecurityFilter implements Filter {

    public static final String ATTRIBUTE_NAME_OF_USERTYPE = "userType";
    public static final String GUEST_JSP_PROPERTY_NAME = "guestJsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
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
        Filter.super.destroy();
    }
}
