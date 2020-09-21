package com.fortech.mockapp.configuration.filters;

import com.fortech.mockapp.configuration.filters.exceptions.AuthorizationHeaderNotFoundException;
import com.fortech.mockapp.utility.SecurityConstants;
import org.springframework.core.env.Environment;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class AuthorizationServerAccessFilter implements Filter {

    private Environment env;

    public AuthorizationServerAccessFilter(Environment env) {
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorizationHeader = httpServletRequest.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
        String method = httpServletRequest.getMethod();
        String authServerRootURL = this.env.getProperty("authServerRootURL");


        if (!method.equals("OPTIONS") && authorizationHeader != null && !httpServletRequest.getHeader("origin").equals(authServerRootURL)) {
            URL url = new URL(SecurityConstants.VERIFY_TOKEN_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader);

            con.getInputStream();
            con.disconnect();

            if (con.getResponseCode() == 200) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                return;
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}


