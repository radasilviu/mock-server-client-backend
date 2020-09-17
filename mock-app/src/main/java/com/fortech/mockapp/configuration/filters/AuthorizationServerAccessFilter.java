package com.fortech.mockapp.configuration.filters;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
@Component
public class AuthorizationServerAccessFilter extends Filter {



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String redirectURL = "http://localhost:8081/oauth/access";
        httpServletResponse.sendRedirect(redirectURL);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

 */


@Component
@Order(1)
public class AuthorizationServerAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeader != null) {
            String redirectURL = "http://localhost:8081/oauth/access";
            httpServletResponse.sendRedirect(redirectURL);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}


