package com.mlk.web.home.manager.filter;

/**
 * Created by malikai on 2018-7-12.
 */

import com.google.common.collect.Maps;
import com.mlk.util.invoke.config.BaseProperties;
import com.mlk.web.home.manager.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class LoginAuthFilter implements Filter {
    private static final ThreadLocal<Map<String, String>> USER_LOCAL_CONTEXT = ThreadLocal.withInitial(Maps::newConcurrentMap);

    private static final String Header_AccessToken = "Access-Token";

    public static final String Header_AccessToken1 = "access_token";

    private static final String Local_Date = "Local-Date";

    private static final String User_Agent = "User-Agent";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            Map<String, String> localContext = USER_LOCAL_CONTEXT.get();
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String accessToken = httpRequest.getHeader(Header_AccessToken);
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    localContext.put(cookie.getName(), cookie.getValue());
                    if (cookie.getName().equals("JWT")) {
                        accessToken = cookie.getValue();
                    }
                }
            }

            if (EmptyUtils.isEmpty(accessToken)) {
                accessToken = httpRequest.getHeader(Header_AccessToken1);
            }
            if (EmptyUtils.isEmpty(accessToken)) {
                accessToken = httpRequest.getHeader(Header_AccessToken1);
            }

            if (EmptyUtils.isNotEmpty(accessToken)) {
                localContext.put(Header_AccessToken1, accessToken);
            }

            String localDate = httpRequest.getHeader(Local_Date);
            if (EmptyUtils.isNotEmpty(localDate)) {
                localContext.put(Local_Date, localDate);
            }

            String userAgent = httpRequest.getHeader(User_Agent);
            if (EmptyUtils.isNotEmpty(userAgent)) {
                localContext.put(User_Agent, userAgent);
            }

            if (EmptyUtils.isNotEmpty(localContext)) {
                USER_LOCAL_CONTEXT.set(localContext);
            }

            chain.doFilter(request, response);
        } finally {
            USER_LOCAL_CONTEXT.get().clear();
        }
    }

    @Override
    public void destroy() {
    }

    public static String getContext(String contextName) {
        return USER_LOCAL_CONTEXT.get().get(contextName);
    }

    public static String setContext(String key, String value) {
        return USER_LOCAL_CONTEXT.get().put(key, value);
    }
}
