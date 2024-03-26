package com.burgas.profilesentences.filter;

import com.burgas.profilesentences.entity.UserType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

import static java.lang.System.out;

@WebFilter(filterName = "logOutFilter", urlPatterns = "/log_out-servlet")
public class LogOutFilter implements Filter {

    public static final String DESTROYING_THE_LOGOUT_FILTER = "Destroying the Logout filter";
    public static final String INITIALIZING_THE_LOGOUT_FILTER = "Initializing the Logout filter";
    public static final String YEARS = " years ";
    public static final String MONTHS = " months ";
    public static final String DAYS = " days ";
    public static final String HOURS = " hours ";
    public static final String MINUTES = " minutes ";
    public static final String SECONDS = " seconds";
    public static final String ATTRIBUTE_NAME_OF_ONLINE_TIME = "onlineTime";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        out.println(INITIALIZING_THE_LOGOUT_FILTER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        Timestamp timestamp = (Timestamp) session.getAttribute(AuthorizationFilter.ATTRIBUTE_NAME_OF_LOGINTIME);
        LocalDateTime logoutTime = LocalDateTime.now();

        Period periodDate = Period.between(timestamp.toLocalDateTime().toLocalDate(), logoutTime.toLocalDate());
        Duration durationTime = Duration.between(timestamp.toLocalDateTime().toLocalTime(), logoutTime.toLocalTime());

        String onlineTime = "";
        if (periodDate.getYears() > 0) {

            //noinspection StringTemplateMigration
            onlineTime = onlineTime.concat(periodDate.getYears() + YEARS + periodDate.getMonths() + MONTHS +
                    periodDate.getDays() + DAYS + durationTime.toHoursPart() + HOURS +
                    durationTime.toMinutesPart() + MINUTES + durationTime.toSecondsPart() + SECONDS);
        } else if (periodDate.getMonths() > 0) {

            //noinspection StringTemplateMigration
            onlineTime = onlineTime.concat(periodDate.getMonths() + MONTHS +
                    periodDate.getDays() + DAYS + durationTime.toHoursPart() + HOURS +
                    durationTime.toMinutesPart() + MINUTES + durationTime.toSecondsPart() + SECONDS);
        } else if (periodDate.getDays() > 0) {

            //noinspection StringTemplateMigration
            onlineTime = onlineTime.concat(periodDate.getDays() + DAYS + durationTime.toHoursPart() + HOURS +
                    durationTime.toMinutesPart() + MINUTES + durationTime.toSecondsPart() + SECONDS);
        } else if (durationTime.toHoursPart() > 0) {

            //noinspection StringTemplateMigration
            onlineTime = onlineTime.concat(durationTime.toHoursPart() + HOURS +
                    durationTime.toMinutesPart() + MINUTES + durationTime.toSecondsPart() + SECONDS);
        } else if (durationTime.toMinutesPart() > 0) {

            //noinspection StringTemplateMigration
            onlineTime = onlineTime.concat(durationTime.toMinutesPart() + MINUTES +
                    durationTime.toSecondsPart() + SECONDS);
        } else if (durationTime.toSecondsPart() > 0) {

            //noinspection StringTemplateMigration
            onlineTime = onlineTime.concat(durationTime.toSecondsPart() + SECONDS);
        }

        out.println(onlineTime);

        session.setAttribute(ATTRIBUTE_NAME_OF_ONLINE_TIME, onlineTime);
        session.setAttribute(SecurityFilter.ATTRIBUTE_NAME_OF_USERTYPE, UserType.GUEST);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

        out.println(DESTROYING_THE_LOGOUT_FILTER);
    }
}
