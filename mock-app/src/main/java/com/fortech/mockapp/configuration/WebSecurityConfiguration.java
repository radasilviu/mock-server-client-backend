package com.fortech.mockapp.configuration;

import com.fortech.mockapp.configuration.filters.AuthorizationServerAccessFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .addFilterBefore(new AuthorizationServerAccessFilter(), SecurityContextPersistenceFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}