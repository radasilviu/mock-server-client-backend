package com.fortech.mockapp.configuration;

import com.fortech.mockapp.configuration.filters.AuthorizationServerAccessFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/user/hello").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationServerAccessFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }




}