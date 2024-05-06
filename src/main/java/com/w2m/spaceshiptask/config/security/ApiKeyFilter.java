package com.w2m.spaceshiptask.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class ApiKeyFilter extends GenericFilterBean {

    private static final String API_KEY = "api-key";

    private final String userApiKey;

    public ApiKeyFilter(String userApiKey) {
        this.userApiKey = userApiKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        var request = (HttpServletRequest) servletRequest;
        String apiKey = request.getHeader(API_KEY);

        if (apiKey != null && apiKey.equals(userApiKey)) {

            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            var response = (HttpServletResponse) servletResponse;
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
        }
    }
}
