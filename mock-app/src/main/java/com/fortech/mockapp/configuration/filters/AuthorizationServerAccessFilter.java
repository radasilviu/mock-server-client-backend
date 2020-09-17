package com.fortech.mockapp.configuration.filters;

import com.fortech.mockapp.utility.SecurityConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AuthorizationServerAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorizationHeader = httpServletRequest.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
        String method = httpServletRequest.getMethod();

        if (!method.equals("OPTIONS") && (authorizationHeader != null || !httpServletRequest.getHeader ("origin").equals("http://localhost:8081"))) {
            URL url = new URL(SecurityConstants.VERIFY_TOKEN_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader);

            InputStream responseStream = con.getInputStream();
            String response = readResponse(responseStream);

            if (response.equals("Valid Token")) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                return;
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private String readResponse(InputStream stream) throws IOException {
        InputStreamReader isReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        return sb.toString();
    }
}


