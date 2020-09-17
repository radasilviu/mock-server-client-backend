package com.fortech.mockapp.configuration.filters;

import com.fortech.mockapp.utility.SecurityConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthorizationServerAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorizationHeader = httpServletRequest.getHeader(SecurityConstants.HEADER_AUTHORIZATION);

        if (authorizationHeader != null) {
            String redirectURL = SecurityConstants.VERIFY_TOKEN_URL;
            httpServletResponse.sendRedirect(redirectURL);
            httpServletResponse.addHeader(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}


