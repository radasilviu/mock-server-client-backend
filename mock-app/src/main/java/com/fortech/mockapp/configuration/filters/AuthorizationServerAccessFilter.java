package com.fortech.mockapp.configuration.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.utility.SecurityConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class AuthorizationServerAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorizationHeader = httpServletRequest.getHeader(SecurityConstants.HEADER_AUTHORIZATION);

        if (authorizationHeader != null || !httpServletRequest.getHeader ("Origin").equals("http://localhost:8081")) {

            URL url = new URL(SecurityConstants.VERIFY_TOKEN_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader);

            InputStream responseStream = con.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            ResponseMessage responseMessage = mapper.readValue(responseStream, ResponseMessage.class);

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }


    }

}


